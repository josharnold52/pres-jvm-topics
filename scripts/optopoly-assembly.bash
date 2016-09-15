#/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
source "$DIR/setup.bash"

#asmtype=-XX:+PrintOptoAssembly
asmtype=-XX:+PrintAssembly

java -XX:-TieredCompilation -XX:+UnlockDiagnosticVMOptions -XX:PrintAssemblyOptions=intel,hsdis-help,intel-mnemonic "$asmtype" jvminternals.optopoly.OptoPoly >"$DIR"/../logs/op-assembly.out


