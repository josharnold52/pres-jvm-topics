# assumes parent script is in same directory
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

export DYLD_LIBRARY_PATH="/Users/JArnold/devtools/ojdk/jdk8u60/hotspot/src/share/tools/hsdis/build/macosx-amd64"

export PROJECT_DIR="$DIR/../"

export JAVA_HOME=/Users/JArnold/devtools/ojdk/jdk8u60/build/macosx-x86_64-normal-server-fastdebug/images/j2sdk-image

export PATH="$JAVA_HOME/bin":"$JAVA_HOME/jre/bin":"$PATH"

export CLASSPATH="$PROJECT_DIR/bin/"
