# toolbox  [![Clojure CI](https://github.com/iomonad/toolbox/actions/workflows/clojure.yml/badge.svg?branch=master)](https://github.com/iomonad/toolbox/actions/workflows/clojure.yml) [![cljdoc badge](https://cljdoc.org/badge/io.trosa/toolbox)](https://cljdoc.org/d/io.trosa/toolbox)

<a href="https://github.com/iomonad/toolbox"><img src=".github/banner.png" height="200" align="right"></a>

> [!NOTE]
A set of utils to work with Clojure projects. This library aims to be **zero dependencies**.

> [!IMPORTANT]
> Work only with JDK >=21

## References

### JVM

#### [Java](https://cljdoc.org/d/io.trosa/toolbox/CURRENT/api/toolbox.jvm.java)

- Properties as Map
- Class methods dump (useful to explore Java libraries)
- Java Runtime informations (via properties)

#### [Classpath](https://cljdoc.org/d/io.trosa/toolbox/CURRENT/api/toolbox.jvm.classpath)

- Clojure project name and version extractor (via leiningen)
- Classpath JAR's listing
- Dependencies listing

### Maths

#### [Linear Algebra](https://cljdoc.org/d/io.trosa/toolbox/CURRENT/api/toolbox.maths.algebra.linear)

- Reduced Row Echelon Form and Applications (RREF) function

#### [Suites](https://cljdoc.org/d/io.trosa/toolbox/CURRENT/api/toolbox.maths.suites)

- Fibonnaci suite

#### [Algos](https://cljdoc.org/d/io.trosa/toolbox/CURRENT/api/toolbox.maths.algos)

### [Collections](https://cljdoc.org/d/io.trosa/toolbox/CURRENT/api/toolbox.collections)

- Deep-merging with collection support

### [FileSystem](https://cljdoc.org/d/io.trosa/toolbox/CURRENT/api/toolbox.fs)

### [Pprint](https://cljdoc.org/d/io.trosa/toolbox/CURRENT/api/toolbox.pprint)

- Method to print `ByteBuffer` in `HexDump` like format.

### [Graph](https://cljdoc.org/d/io.trosa/toolbox/CURRENT/api/toolbox.graph)

### [Intervals](https://cljdoc.org/d/io.trosa/toolbox/CURRENT/api/toolbox.intervals)

- Overlapping intervals merge algorithm

### [Regexes](https://cljdoc.org/d/io.trosa/toolbox/CURRENT/api/toolbox.regexes)

### [Set](https://cljdoc.org/d/io.trosa/toolbox/CURRENT/api/toolbox.set)

- `PowerSet` implementation

### [Binary](https://cljdoc.org/d/io.trosa/toolbox/CURRENT/api/toolbox.binary)

- `ByteBuffer` NIO thin wrapper

## Changelog

### UNRELEASED

## License

Copyright © 2024-2025 iomonad <iomonad@riseup.net>

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
