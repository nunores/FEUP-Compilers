
/**
 * Copyright 2021 SPeCS.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License. under the License.
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import pt.up.fe.comp.TestUtils;
import pt.up.fe.comp.jmm.jasmin.JasminResult;
import pt.up.fe.comp.jmm.ollir.OllirResult;
import pt.up.fe.comp.jmm.ollir.OllirUtils;
import pt.up.fe.specs.util.SpecsIo;

import java.util.ArrayList;


public class JasminTest {

    /* Correct files */

    @Test
    public void testHelloWorld() {
        //JasminResult result = TestUtils.backend(new OllirResult(OllirUtils.parse(SpecsIo.getResource("fixtures/public/ollir/myclass1.ollir")), null, new ArrayList<>()));
        var result = TestUtils.backend(new OllirResult(OllirUtils.parse(SpecsIo.getResource("fixtures/public/ollir/myclass1.ollir")), null, new ArrayList<>()));

    
        // String output = result.run().trim();
        // System.out.println(output);
    }


}
