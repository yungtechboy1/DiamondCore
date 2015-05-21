from struct import pack
from struct import unpack

class OutputStream():
    __buffer = bytearray()

    def __init__(self):
        self.__buffer = bytearray()

    def put(self, x):
        for y in range(0, len(x)):
            self.putByte(x[y])

    def putByte(self, x):
        self.__buffer.append(x)

    def putBoolean(self, x):
        if isinstance(x, bool):
            if(x == True):
                self.putByte(0x01)
            else:
                self.putByte(0x00)
        else:
            raise TypeError("Value must be a boolean!")

    def putChar(self, x):
        if isinstance(x, chr):
            y = pack(">c", x)
            self.put(y)
        else:
            raise TypeError("Value must be an character!")

    def putShort(self, x):
        if isinstance(x, int):
            y = pack(">h", x)
            self.put(y)
        else:
            raise TypeError("Value must be an integer/short!")

    def putUnsignedShort(self, x):
        if isinstance(x, int):
            y = pack(">H", x)
            self.put(y)
        else:
            raise TypeError("Value must be an integer/short!")

    def putInt(self, x):
        if isinstance(x, int):
            y = pack(">i", x)
            self.put(y)
        else:
            raise TypeError("Value must be an integer!")

    def putUnsignedInt(self, x):
        if isinstance(x, int):
            y = pack(">I", x)
            self.put(y)
        else:
            raise TypeError("Value must be an integer!")

    def putLong(self, x):
        if isinstance(x, int):
            y = pack(">l", x)
            self.put(y)
        else:
            raise TypeError("Value must be an integer!")

    def putUnsignedLong(self, x):
        if isinstance(x, int):
            y = pack(">L", x)
            self.put(y)
        else:
            raise TypeError("Value must be an integer!")

    def putFloat(self, x):
        if isinstance(x, float):
            y = pack(">f", x)
            self.put(y)
        else:
            raise TypeError("Value must be a float!")

    def putDouble(self, x):
        if isinstance(x, int):
            y = pack(">d", x)
            self.put(y)
        else:
            raise TypeError("Value must be an integer!")

    def putString(self, x):
        if isinstance(x, str):
            self.putUnsignedShort(len(x))
            y = (x+str("\r\n")).encode('UTF-8')
            self.put(y)
        else:
            raise TypeError("Value must be a string!")

    def toArray(self):
        return self.__buffer

class InputStream():
    __buffer = None
    __position = -1

    def __init__(self, data):
        self.__buffer = data
        self.__position = 0

    def get(self, len):
        buffer = bytearray()
        for x in range(0, len):
            buffer.append(self.getByte())
        return buffer

    def getByte(self):
        self.__position += 1
        return self.__buffer[self.__position-1]

    def getBoolean(self):
        if(self.getByte() == 0x01):
            return True
        else:
            return False

    def getChar(self):
        return chr(self.getUnsignedShort())

    def getShort(self):
        s = self.get(2)
        return unpack(">h", s)[0]

    def getUnsignedShort(self):
        s = self.get(2)
        return unpack(">H", s)[0]

    def getInt(self):
        i = self.get(4)
        return unpack(">i", i)[0]

    def getUnsignedInt(self):
        i = self.get(4)
        return unpack(">I", i)[0]

    def getLong(self):
        l = self.get(8)
        return unpack(">l", l)[0]

    def getUnsignedLong(self):
        l = self.get(8)
        return unpack(">L", l)[0]

    def getFloat(self):
        f = self.get(4)
        return unpack(">f", f)[0]

    def getDouble(self):
        d = self.get(8)
        return unpack(">d", d)[0]

    def getString(self):
        len = self.getUnsignedShort()
        data = self.get(len)
        utf = None
        for x in range(0, len):
            utf += (chr(data[x]))
        return utf

    def skip(self, length):
        self.__position += length

    def rewind(self, length):
        self.__position -= length

    def len(self):
        return len(self.__buffer)