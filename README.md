# nsdlib

[![Build Status](https://travis-ci.org/meyfa/nsdlib.svg?branch=master)](https://travis-ci.org/meyfa/nsdlib)

This is a Java library for working with [Nassi-Shneiderman diagrams](https://en.wikipedia.org/wiki/Nassi–Shneiderman_diagram)
(structograms).

It offers the following functionality:

* **Construct elements through code**
  - All standard block types supported
* **Read structograms from files**
  - Currently only support for the [Structorizer](http://structorizer.fisch.lu/)
    format, but easy to add your own
* **Render structograms as images**
  - Basic layouting, dynamic size calculation
  - High amount of abstraction through `RenderAdapter` (AWT adapter included;
    support for custom adapters for other targets, e.g. Android)



## Reading a Structogram

The following code reads a structogram stored in Structorizer's XML format.

```java
NSDReader reader = new StructorizerReader();

NSDRoot root;

File file = new File("/path/to/file.nsd");
try (FileInputStream in = new FileInputStream(file)) {
    root = reader.read(in);
} catch (NSDReaderException | IOException e) {
    e.printStackTrace();
}

// do something with `root`
```

Feel free to submit a PR or open an issue if you require support for other
formats. The custom reader should implement the `NSDReader` interface.



## Rendering a Structogram

To render a structogram (or part of one), the following steps need to be done:

1. Convert the element into a `RenderPart` (do this for the root element).
2. Layout the render part.
3. Render the actual image.

The following example uses the `AwtRenderer` for rendering to a `BufferedImage`,
but you can easily substitute a custom renderer instead.

```java
AwtRenderer renderer = new AwtRenderer();

// 1. convert (`diagram` is an instance of `NSDRoot`)
RenderPart part = diagram.toRenderPart();

// 2. layout
part.layout(renderer.createContext());
// optional: get the resulting size
// Size s = part.getSize();

// 3. render
BufferedImage img = renderer.render(part);
```



## Manual Structogram Construction

Constructing a structogram with code is as simple as invoking a few
constructors.

```java
NSDRoot diagram = new NSDRoot("When start clicked");

NSDDecision dec = new NSDDecision("(pick random 1 to 10) = 1");
{
    dec.getThen().addChild(new NSDInstruction("say \"You're lucky!\""));
    dec.getElse().addChild(new NSDInstruction("say \"Not so lucky.\""));
}
diagram.addChild(dec);

NSDForever forever = new NSDForever(Arrays.asList(
    new NSDInstruction("wait 1 secs")
));
diagram.addChild(forever);

// etc
```



## Element Types

The base class for every element is `nsdlib.elements.NSDElement`. Elements that
contain other elements extend its subclass `nsdlib.elements.NSDContainer`.

| Type                 | Package                        | Class              |
| -------------------- | ------------------------------ | ------------------ |
| Root element         | `nsdlib.elements`              | `NSDRoot`          |
| Instruction          | `nsdlib.elements`              | `NSDInstruction`   |
| If/Then/Else         | `nsdlib.elements.alternatives` | `NSDDecision`      |
| Switch-Case          | `nsdlib.elements.alternatives` | `NSDCase`          |
| Test-first loop      | `nsdlib.elements.loops`        | `NSDTestFirstLoop` |
| Test-last loop       | `nsdlib.elements.loops`        | `NSDTestLastLoop`  |
| Unconditional loop   | `nsdlib.elements.loops`        | `NSDForever`       |
| Concurrent execution | `nsdlib.elements.parallel`     | `NSDParallel`      |
