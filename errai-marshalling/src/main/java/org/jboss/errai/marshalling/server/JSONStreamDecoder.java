/*
 * Copyright 2010 JBoss, a divison Red Hat, Inc.                              
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.jboss.errai.marshalling.server;

import org.jboss.errai.marshalling.client.api.json.EJValue;
import org.jboss.errai.marshalling.server.json.impl.ErraiJSONValue;

import java.io.*;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.pow;

/**
 * High-performance stream JSON parser. Provides the decoding algorithm to interpret the Errai Wire Protcol,
 * including serializable types.  This parser always assumes the outer payload is a Map. So it probably shouldn't
 * be used as a general parser.
 *
 * @author Mike Brock
 * @since 1.1
 */
public class JSONStreamDecoder {
  private CharBuffer buffer;
  private BufferedReader reader;

  private char carry;

  private int read;
  private boolean initial = true;

  public JSONStreamDecoder(InputStream inStream) {
    this.buffer = CharBuffer.allocate(25);
    try {
      this.reader = new BufferedReader(
              new InputStreamReader(inStream, "UTF-8")
      );
    }
    catch (UnsupportedEncodingException e) {
      throw new Error("UTF-8 is not supported by this JVM?", e);
    }
  }

  public static EJValue decode(InputStream instream) throws IOException {
    return new JSONStreamDecoder(instream).parse();
  }

  public char read() throws IOException {
    if (carry != 0) {
      try {
        return carry;
      }
      finally {
        carry = 0;
      }
    }
    if (read <= 0) {
      if (!initial) buffer.rewind();
      initial = false;
      if ((read = reader.read(buffer)) <= 0) {
        return 0;
      }
      buffer.rewind();
    }
    read--;
    return buffer.get();
  }

  public EJValue parse() {
    try {
      return new ErraiJSONValue(_parse(new Context(), null));
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private Object _parse(Context ctx, Object collection) throws IOException {
    char c;
    StringBuilder appender;
    while ((c = read()) != 0) {
      switch (c) {
        case '[':
          ctx.addValue(_parse(new Context(), new ArrayList()));
          break;

        case '{':
          ctx.addValue(_parse(new Context(), new HashMap()));
          break;

        case ']':
        case '}':
          return ctx.record(collection);

        case ',':
          ctx.record(collection);
          break;

        case '"':
        case '\'':
          char term = c;
          appender = new StringBuilder(100);
          StrCapture:
          while ((c = read()) != 0) {
            switch (c) {
              case '\\':
                appender.append(handleEscapeSequence());
                break;
              case '"':
              case '\'':
                if (c == term) {
                  ctx.addValue(appender.toString());
                  term = 0;
                  break StrCapture;
                }
              default:
                appender.append(c);
            }
          }

          if (term != 0) {
            throw new RuntimeException("unterminated string literal");
          }

          break;

        case ':':
          continue;

        default:
          if (isValidNumberPart(c)) {
            ctx.addValue(parseNumber(c));
            break;
          }
          else if (Character.isJavaIdentifierPart(c)) {
            appender = new StringBuilder(100).append(c);

            while (((c = read()) != 0) && Character.isJavaIdentifierPart(c)) {
              appender.append(c);
            }

            String s = appender.toString();

            if (s.length() > 5) ctx.addValue(s);
            else if ("null".equals(s)) {
              ctx.addValue(null);
            }
            else if ("true".equals(s)) {
              ctx.addValue(Boolean.TRUE);
            }
            else if ("false".equals(s)) {
              ctx.addValue(Boolean.FALSE);
            }
            else {
              ctx.addValue(s);
            }

            if (c != 0) carry = c;
          }
      }
    }

    return ctx.record(collection);
  }

  public char handleEscapeSequence() throws IOException {
    char c;
    switch (c = read()) {
      case '\\':
        return '\\';
      case '/':
        return '/';
      case 'b':
        return '\b';
      case 'f':
        return '\f';
      case 't':
        return '\t';
      case 'r':
        return '\r';
      case 'n':
        return '\n';
      case '\'':
        return '\'';
      case '"':
        return '\"';
      case 'u':
        //handle unicode
        char[] unicodeSeq = new char[4];
        int i = 0;
        for (; i < 4 && isValidHexPart(c = read()); i++) {
          unicodeSeq[i] = c;
        }
        if (i != 4) {
          throw new RuntimeException("illegal unicode escape sequence: expected 4 hex characters after \\u");
        }

        return (char) Integer.decode("0x" + new String(unicodeSeq)).intValue();

      default:
        throw new RuntimeException("illegal escape sequence: " + c);
    }
  }

  public double parseNumber(char c) throws IOException {
    double val = 0, dVal = 0, factor = 1, exp = 0;

    char[] buf = new char[21];
    int len = 0;
    do {
      buf[len++] = c;
    }
    while ((c = read()) != 0 && isValidNumberPart(c));

    if (c == 'e' || c == 'E') {
      if ((c = read()) == '+') {
        c = read();
      }

      exp = parseNumber(c);
    }
    else if (c != 0) {
      carry = c;
    }

    if (len == 1 && buf[0] == '-') return -0;

    for (int i = len - 1; i != -1; i--) {
      switch (buf[i]) {
        case '.':
          dVal = val / factor;
          val = 0;
          factor = 1;
          continue;
        case '-':
          if (i != 0) {
            throw new NumberFormatException(new String(buf));
          }
          val = -val;
          break;
        case '1':
          val += factor;
          break;
        case '2':
          val += 2 * factor;
          break;
        case '3':
          val += 3 * factor;
          break;
        case '4':
          val += 4 * factor;
          break;
        case '5':
          val += 5 * factor;
          break;
        case '6':
          val += 6 * factor;
          break;
        case '7':
          val += 7 * factor;
          break;
        case '8':
          val += 8 * factor;
          break;
        case '9':
          val += 9 * factor;
          break;
      }

      factor *= 10;
    }

    if (exp != 0) {
      return (dVal + val) * pow(10, exp);
    }
    else {
      return dVal + val;
    }
  }

  private static boolean isValidNumberPart(char c) {
    switch (c) {
      case '.':
      case '-':
      case '0':
      case '1':
      case '2':
      case '3':
      case '4':
      case '5':
      case '6':
      case '7':
      case '8':
      case '9':
        return true;
      default:
        return false;
    }
  }

  private static boolean isValidHexPart(char c) {
    switch (c) {
      case '.':
      case '-':
      case '0':
      case '1':
      case '2':
      case '3':
      case '4':
      case '5':
      case '6':
      case '7':
      case '8':
      case '9':
      case 'A':
      case 'B':
      case 'C':
      case 'D':
      case 'E':
      case 'F':
        return true;
      default:
        return false;
    }
  }

  private static class Context {
    Object lhs;
    Object rhs;

    private Context() {
    }

    private Object addValue(Object val) {
      if (lhs == null) {
        return lhs = val;
      }
      else {
        return rhs = val;
      }
    }

    @SuppressWarnings("unchecked")
    private Object record(Object collection) {
      try {
        if (lhs != null) {
          if (collection instanceof Map) {
            ((Map) collection).put(lhs, rhs);
          }
          else {
            if (collection == null) return lhs;
            ((Collection) collection).add(lhs);
          }
        }
        return collection;
      }
      finally {
        lhs = rhs = null;
      }
    }
  }
}
