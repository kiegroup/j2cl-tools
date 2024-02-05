# GWT/J2CL emulation for `java.nio` packages

To use in GWT2, depend on this Jar in your project, and add this to
your `.gwt.xml` module file:

    ```xml
    <dependency>
        <groupId>org.kie.j2cl.tools.nio</groupId>
        <artifactId>gwt-nio</artifactId>
        <version>LATEST_VERSION</version>
    </dependency>
    ```


    <inherits name="org.gwtproject.nio.GwtNioSupport" />

The `org.gwtproject.nio.TypedArrayHelper` class contains to helper 
methods, to turn `java.nio.ByteBuffer`s into JavaScript `ArrayBuffer` 
(technically `ArrayBufferView`, but you can get an `ArrayBuffer` from 
there) and back again.

#### Take care in forking this repository, we may rewrite history to remove unrelated PlayN commits.
