/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package java.io;

/**
 * A class for turning a byte stream into a character stream. Data read from the source input stream
 * is converted into characters by either a default or a provided character converter. The default
 * encoding is taken from the "file.encoding" system property. {@code InputStreamReader} contains a
 * buffer of bytes read from the source stream and converts these into characters as needed. The
 * buffer size is 8K.
 *
 * @see OutputStreamWriter
 */
public class InputStreamReader extends Reader {

  private InputStream in;

  private Utf8Decoder decoder;

  /**
   * Constructs a new {@code InputStreamReader} on the {@link InputStream} {@code in}. This
   * constructor sets the character converter to the encoding specified in the "file.encoding"
   * property and falls back to ISO 8859_1 (ISO-Latin-1) if the property doesn't exist.
   *
   * @param in the input stream from which to read characters.
   */
  public InputStreamReader(InputStream in) {
    this.in = in;
    decoder = new Utf8Decoder();
  }

  /**
   * Constructs a new InputStreamReader on the InputStream {@code in}. The character converter that
   * is used to decode bytes into characters is identified by name by {@code enc}. If the encoding
   * cannot be found, an UnsupportedEncodingException error is thrown.
   *
   * @param in the InputStream from which to read characters.
   * @param enc identifies the character converter to use.
   * @throws NullPointerException if {@code enc} is {@code null}.
   * @throws UnsupportedEncodingException if the encoding specified by {@code enc} cannot be found.
   */
  public InputStreamReader(InputStream in, final String enc) throws UnsupportedEncodingException {
    if (enc == null) {
      throw new NullPointerException();
    }
    this.in = in;
    decoder = new Utf8Decoder();
  }

  /**
   * Closes this reader. This implementation closes the source InputStream and releases all local
   * storage.
   *
   * @throws IOException if an error occurs attempting to close this reader.
   */
  @Override
  public void close() throws IOException {
    decoder = null;
    if (in != null) {
      in.close();
      in = null;
    }
  }

  /**
   * Reads a single character from this reader and returns it as an integer with the two
   * higher-order bytes set to 0. Returns -1 if the end of the reader has been reached. The byte
   * value is either obtained from converting bytes in this reader's buffer or by first filling the
   * buffer from the source InputStream and then reading from the buffer.
   *
   * @return the character read or -1 if the end of the reader has been reached.
   * @throws IOException if this reader is closed or some other I/O error occurs.
   */
  @Override
  public int read() throws IOException {
    if (!isOpen()) {
      throw new IOException("InputStreamReader is closed.");
    }
    char buf[] = new char[1];
    return read(buf, 0, 1) != -1 ? buf[0] : -1;
  }

  /**
   * Reads at most {@code length} characters from this reader and stores them at position {@code
   * offset} in the character array {@code buf}. Returns the number of characters actually read or
   * -1 if the end of the reader has been reached. The bytes are either obtained from converting
   * bytes in this reader's buffer or by first filling the buffer from the source InputStream and
   * then reading from the buffer.
   *
   * @param buf the array to store the characters read.
   * @param offset the initial position in {@code buf} to store the characters read from this
   *     reader.
   * @param length the maximum number of characters to read.
   * @return the number of characters read or -1 if the end of the reader has been reached.
   * @throws IndexOutOfBoundsException if {@code offset < 0} or {@code length < 0}, or if {@code
   *     offset + length} is greater than the length of {@code buf}.
   * @throws IOException if this reader is closed or some other I/O error occurs.
   */
  @Override
  public int read(char[] buf, int offset, int length) throws IOException {
    if (!isOpen()) {
      throw new IOException("InputStreamReader is closed.");
    }
    if (offset < 0 || offset > buf.length - length || length < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (length == 0) {
      return 0;
    }

    byte[] buffer = new byte[length];
    int count = 0;
    try {
      count = in.read(buffer);
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (count <= 0) {
      return count;
    }

    return decoder.decode(buffer, 0, count, buf, offset);
  }

  /*
   * Answer a boolean indicating whether or not this InputStreamReader is
   * open.
   */
  private boolean isOpen() {
    return in != null;
  }

  /**
   * Indicates whether this reader is ready to be read without blocking. If the result is {@code
   * true}, the next {@code read()} will not block. If the result is {@code false} then this reader
   * may or may not block when {@code read()} is called. This implementation returns {@code true} if
   * there are bytes available in the buffer or the source stream has bytes available.
   *
   * @return {@code true} if the receiver will not block when {@code read()} is called, {@code
   *     false} if unknown or blocking will occur.
   * @throws IOException if this reader is closed or some other I/O error occurs.
   */
  @Override
  public boolean ready() throws IOException {
    if (in == null) {
      throw new IOException("InputStreamReader is closed.");
    }
    try {
      return in.available() > 0;
    } catch (IOException e) {
      return false;
    }
  }

  /**
   * Utf8Decoder converts UTF-8 encoded bytes into characters properly handling buffer boundaries.
   *
   * <p>This class is stateful and up to 4 calls to {@link #decode(byte)} may be needed before a
   * character is appended to the char buffer.
   *
   * <p>The UTF-8 decoding is done by this class and no additional buffers are created. The UTF-8
   * code was inspired by http://bjoern.hoehrmann.de/utf-8/decoder/dfa/
   *
   * @author davebaol
   */
  public static class Utf8Decoder {

    private static final char REPLACEMENT = '\ufffd';
    private static final int UTF8_ACCEPT = 0;
    private static final int UTF8_REJECT = 12;

    // This table maps bytes to character classes to reduce
    // the size of the transition table and create bitmasks.
    private static final byte[] BYTE_TABLE = {
            // @off - disable libGDX formatter
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0,
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9,
            9,
            7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
            7,
            8, 8, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2,
            10, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 11, 6, 6, 6, 5, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8,
            8
            // @on - enable libGDX formatter
    };

    // This is a transition table that maps a combination of a
    // state of the automaton and a character class to a state.
    private static final byte[] TRANSITION_TABLE = {
            // @off - disable libGDX formatter
            0, 12, 24, 36, 60, 96, 84, 12, 12, 12, 48, 72, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12,
            12, 0, 12, 12, 12, 12, 12, 0, 12, 0, 12, 12, 12, 24, 12, 12, 12, 12, 12, 24, 12, 24, 12, 12,
            12, 12, 12, 12, 12, 12, 12, 24, 12, 12, 12, 12, 12, 24, 12, 12, 12, 12, 12, 12, 12, 24, 12,
            12,
            12, 12, 12, 12, 12, 12, 12, 36, 12, 36, 12, 12, 12, 36, 12, 12, 12, 12, 12, 36, 12, 36, 12,
            12,
            12, 36, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12
            // @on - enable libGDX formatter
    };

    private int codePoint;
    private int state;
    private final char[] utf16Char = new char[2];
    private char[] charBuffer;
    private int charOffset;

    public Utf8Decoder() {
      this.state = UTF8_ACCEPT;
    }

    protected void reset() {
      state = UTF8_ACCEPT;
    }

    public int decode(byte[] b, int offset, int length, char[] charBuffer, int charOffset) {
      this.charBuffer = charBuffer;
      this.charOffset = charOffset;
      int end = offset + length;
      for (int i = offset; i < end; i++) decode(b[i]);
      return this.charOffset - charOffset;
    }

    private void decode(byte b) {

      if (b > 0 && state == UTF8_ACCEPT) {
        charBuffer[charOffset++] = (char) (b & 0xFF);
      } else {
        int i = b & 0xFF;
        int type = BYTE_TABLE[i];
        codePoint = state == UTF8_ACCEPT ? (0xFF >> type) & i : (i & 0x3F) | (codePoint << 6);
        int next = TRANSITION_TABLE[state + type];

        switch (next) {
          case UTF8_ACCEPT:
            state = next;
            if (codePoint < Character.MIN_HIGH_SURROGATE) {
              charBuffer[charOffset++] = (char) codePoint;
            } else {
              // The code below is equivalent to
              // for (char c : Character.toChars(codePoint)) charBuffer[charOffset++] = c;
              // but does not allocate a char array.
              int codePointLength = Character.toChars(codePoint, utf16Char, 0);
              charBuffer[charOffset++] = utf16Char[0];
              if (codePointLength == 2) charBuffer[charOffset++] = utf16Char[1];
            }
            break;

          case UTF8_REJECT:
            codePoint = 0;
            state = UTF8_ACCEPT;
            charBuffer[charOffset++] = REPLACEMENT;
            break;

          default:
            state = next;
            break;
        }
      }
    }
  }
}
