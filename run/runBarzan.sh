 java -XX:+HeapDumpOnOutOfMemoryError -Xmx8g -cp `run/classpath.sh` com.oltpbenchmark.DBWorkload -b wikipedia -c config/rates-wiki100k-io -o ~/trans-wiki100k-io &> ~/err-wiki100k-io &
