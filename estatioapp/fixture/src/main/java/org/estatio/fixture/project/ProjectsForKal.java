/*
 *
 *  Copyright 2012-2014 Eurocommercial Properties NV
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.estatio.fixture.project;

import org.estatio.fixture.party.OrganisationForMediaXNl;
import org.estatio.fixture.party.PersonForJohnDoeNl;
import org.estatio.fixture.party.PersonForLinusTorvaldsNl;

import static org.incode.module.base.integtests.VT.ld;

public class ProjectsForKal extends ProjectAbstract {

    public static final String PROJECT_REFERENCE = "PR1";
    public static final String PROJECT_REFERENCE2 = "PR2";

    @Override
    protected void execute(ExecutionContext executionContext) {

        // prereqs
        executionContext.executeChild(this, new OrganisationForMediaXNl());
        executionContext.executeChild(this, new PersonForJohnDoeNl());
        executionContext.executeChild(this, new PersonForLinusTorvaldsNl());

        // exec
        createProject(
        		PROJECT_REFERENCE, "Augment parkingplace", ld(1999, 1, 1), ld(1999, 7, 1), null,
                "/NLD", null, executionContext);
        
        createProject(
        		PROJECT_REFERENCE2, "Broaden entrance", ld(1999, 4, 1), ld(1999, 5, 1), null,
                "/NLD", null, executionContext);
    }

}
