#/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
source "$DIR/setup.bash"

java -Xmx4g -Xms4g -XX:+UseConcMarkSweepGC -Xloggc:"$DIR"/../logs/lru-gc.log \
-XX:+PrintGC \
-XX:-PrintGCCause \
-XX:+PrintGCDetails \
-XX:+PrintGCDateStamps \
-XX:+PrintGCTimeStamps \
-XX:+PrintTenuringDistribution \
jvminternals.gclru.LruExample "$@"


