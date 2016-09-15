#/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
source "$DIR/setup.bash"

java -XX:+PrintCompilation -XX:-TieredCompilation  jvminternals.opto1.Opto1

