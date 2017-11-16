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
package org.estatio.module.application.fixtures;

import javax.inject.Inject;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.estatio.module.charge.EstatioChargeModule;
import org.estatio.module.currency.EstatioCurrencyModule;
import org.estatio.module.index.EstatioIndexModule;
import org.estatio.module.link.EstatioLinkModule;
import org.estatio.module.tax.EstatioTaxModule;

public class EstatioReferenceDataTeardownFixture extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        executionContext.executeChild(this, new EstatioCurrencyModule().getTeardownFixture());

        executionContext.executeChild(this, new EstatioChargeModule().getTeardownFixture());

        executionContext.executeChild(this, new EstatioTaxModule().getTeardownFixture());

        executionContext.executeChild(this, new EstatioIndexModule().getTeardownFixture());

        executionContext.executeChild(this, new EstatioLinkModule().getTeardownFixture());

    }

    protected void deleteFrom(final Class cls) {
        preDeleteFrom(cls);
        doDeleteFrom(cls);
        postDeleteFrom(cls);
    }

    private void doDeleteFrom(final Class cls) {
        isisJdoSupport.deleteAll(cls);
    }

    protected void preDeleteFrom(final Class cls) {}

    protected void postDeleteFrom(final Class cls) {}

    @Inject
    private IsisJdoSupport isisJdoSupport;

}