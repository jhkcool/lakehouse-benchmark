/*
 * Copyright 2020 by OLTPBenchmark Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.oltpbenchmark.benchmarks.chbenchmart4impala.queries;

import com.oltpbenchmark.api.SQLStmt;

import static com.oltpbenchmark.benchmarks.chbenchmart4impala.TableNames.*;

public class Q5 extends GenericQuery {

    public final SQLStmt query_stmt = new SQLStmt(
            "SELECT n_name, "
                    + "sum(ol_amount) AS revenue "
                    + "FROM (SELECT r_regionkey,o_c_id,o_w_id,o_d_id,o_id FROM"
                    + "" +region() + ", "
                    + "" +oorder() + " "
                    + "WHERE r_name = 'Europe' AND o_entry_d >= CAST('2007-01-02 00:00:00.000000' AS TIMESTAMP)) AS t1 join "
                    + "" +customer() + ", "
                    + "" +stock() + ", "
                    + "" +supplier() + ", "
                    + "" +nation() + ", "
                    + "" +order_line() + " "
                    + "WHERE c_id = t1.o_c_id "
                    + "AND c_w_id = t1.o_w_id "
                    + "AND c_d_id = t1.o_d_id "
                    + "AND MOD((s_w_id * s_i_id), 10000) = su_suppkey "
                    + "AND ascii(cast(substring(c_state, 1, 1) as varchar(1))) = su_nationkey "
                    + "AND su_nationkey = n_nationkey "
                    + "AND n_regionkey = r_regionkey "
                    + "AND ol_o_id = t1.o_id "
                    + "AND ol_w_id = t1.o_w_id "
                    + "AND ol_d_id = t1.o_d_id "
                    + "AND ol_w_id = s_w_id "
                    + "AND ol_i_id = s_i_id "
                    + "GROUP BY n_name "
                    + "ORDER BY revenue DESC"
    );

    protected SQLStmt get_query() {
        return query_stmt;
    }

    public static void main(String[] args) {
        System.out.println(new Q5().query_stmt.getSQL());
    }
}
