import sys
import socket
from net.trenterprises.python.io.stream import *

sock = socket.socket()
sock.connect(("localhost", 25833))

lego = OutputStream()
lego.putByte(0x00)

sock.send(lego.toArray())

sock.close()

print("SUCCESS")