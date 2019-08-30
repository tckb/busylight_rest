# Busylight Driver
The project `busylight-core` is a complete and comprehensive driver implementation of the protocol specification of Busylight for revision 2.2.  The implementation is structured logically into the following ---

Low-level Api:

*   `ProtocolSpec`
    
    
*   `ProtocolStep`

    
*   `*.protocol.bytes.*`
    
    

High-level Api:

* `SpecCoonstants`
    

The `Driver` class implements the logic to send the raw byte buffer data to the `HidDevice`. Apart from this, the class is also responsible for maintaining a  _stable_ connection to the device. That means, it _observes_ for attachments, and detachment of the device and tries to reestablish the connection. 

Additionally, the driver is responsible for sending out this so-called **_Keep-Alive**_ command signal to the device (--to keep the previous data signal alive) periodically. This is means the scenario of attachment, followed by a detachment is handled.

       