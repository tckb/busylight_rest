# Busylight Driver
The project `busylight-core` is a complete and comprehensive driver implementation of the protocol specification of Busylight for revision 2.2. The purpose of this module/project is to only act as a driver for communicating with the busylight device. Most common interactions are already covered in the "high-level" api. For custom specification, you can use the `ProtocolSpec#builder`    

The implementation is structured logically into the following ---

## Low-level Api:

*   `ProtocolSpec`
        
      TBA
    
*   `ProtocolStep`
    
    TBA
    
*   `*.protocol.bytes.*`
    
    TBA
    
## High-level Api:

* `SpecConstants`

    TBA
    
## Driver
The `Driver` class implements the logic to send the raw byte buffer data to the `HidDevice`. Apart from this, the class is also responsible for maintaining a  _stable_ connection to the device. That means, it _observes_ for attachments, and detachment of the device and tries to reestablish the connection. 

Additionally, the driver is responsible for sending out this so-called **_Keep-Alive**_ command signal to the device (--to keep the previous data signal alive) periodically. This is means the scenario of attachment, followed by a detachment is handled.


## Usage 

#### Pre-defined specs 
```java
try (Driver driver = Driver.tryAndAcquire()) {
    System.out.println("****** PLAYING MUSIC *******");
    driver.send(SpecConstants.toneSpec(Tone.forTone(Tones.OPENOFFICE, 1)));
   
    System.out.println("****** PLAYING STANDARD SPECS *******");
    for (StandardSpecs spec : StandardSpecs.values()) {
      driver.send(spec);
      Thread.sleep(2000);
    }
   
    System.out.println("****** PLAYING LIGHT SPECS *******");
   
    for (Light light : Light.values()) {
      driver.send(SpecConstants.lightSpec(light));
      Thread.sleep(1000);
    }
   
    System.out.println("****** PLAYING BLINK SPECS *******");
   
    for (Light light : Light.values()) {
      driver.send(SpecConstants.blinkSpec(light));
      Thread.sleep(5000);
    }
    driver.send(StandardSpecs.OFF);
}
```
#### Custom spec
```java

```