#/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
source "$DIR/setup.bash"

java -XX:-TieredCompilation  jvminternals.optovolatile.OptoVolatile "$@" 


