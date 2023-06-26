# scale-complexes

Compute scale complexes of formal contexts.

## Usage

The standalone jar can be found in `builds/uberjar`. It can be used with: 

    $ java -jar scale-complexes-0.1.0-SNAPSHOT-standalone.jar [args]

The input file (a context in burmeister format) and the output directory must be specified in `args`. Available command-line arguments:

```
  -l, --load FILE    Load file path (for context file in burmeister format).
  -s, --save FOLDER  Save folder path (for resulting scale-complex files).
  -h, --help
```

## Examples

   $ java -jar scale-complexes-0.1.0-SNAPSHOT-standalone.jar -l ../../testing-data/Animals.ctx -s ../../example-complexes

## License

Copyright © 2023

Distributed under the Eclipse Public License.


