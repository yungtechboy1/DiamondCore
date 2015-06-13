Pocket Minecraft Protocol
==================

Data Formats
==================

Before we start talking about the actual protocol, we must list each data type and how they are written/readen.

Data Type | Description
--------- | -----------
Byte      | Single byte consiting of 8 bits. This has a range of -127 to 127 signed, or 0 to 255 unsigned.
Char      | A single unicode value, also functions as an unsigned short.
Short     | An integer consisting of two bytes. Has a range of -32,768 to 32,767 signed, or 0 to 65,535 unsigned.
Integer   | An intenger consisting of four bytes. Has a range of −2,147,483,648 to 2,147,483,647 signed, or 0 to 4,924,967,295 unsigned.
Long      | An integer consisting of eight bytes. Has a range of −9,223,372,036,854,775,808 to 9,223,372,036,854,775,807 signed, or 0 to 18,446,744,073,709,551,615 unsigned
Float     | A four byte double. Has a range of 1.40129846432481707e-45 to 3.40282346638528860e+38 signed, and 0 to ??? unsigned.
Double    | An eight byte double. Has a range 4.94065645841246544e-324d to 1.79769313486231570e+308d signed, and 0 to ??? unsigned
Boolean   | A single byte, the value 0x01 consists it is true and 0x00 consists it is false.
String    | A byte array prefixed by an signed short. The array can be gotten using "STRING".getBytes() in java.


Packets
=================
Each packet in Pocket Minecraft has a packet with the format of the ID and the packet itself.
There are also CustomPackets, InternalPackets, which we will get to later.

ID_UNCONNECTED_PING_OPEN_CONNECTIONS
-----------------
Field Name | Type   | Value
---------------------------
Packet ID  | Byte   | 0x1C
Ping ID    | Long   | Ping ID sent by client in ID_CONNECTED_PING_OPEN_CONNECTIONS
MAGIC      | MAGIC  | 0x00ffff00fefefefefdfdfdfd12345678
Identifier | String | A string put together in this order: "MCPE;Steve;Protocol;ProtocolName;OnlinePlayers;MaxPlayers"


