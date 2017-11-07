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
package org.estatio.dom.lease.invoicing.viewmodel.dnc;

import com.google.common.base.Predicate;

import org.apache.isis.applib.annotation.Mixin;

import org.estatio.dom.invoice.DocumentTypeData;
import org.estatio.dom.invoice.Invoice;
import org.estatio.module.lease.dom.invoicing.summary.InvoiceSummaryForPropertyDueDateStatus;

/**
 * TODO: REVIEW: this mixin could in theory be inlined, but inherits substantial functionality from superclass and maybe we want to keep invoices and documents decoupled?
 */
@Mixin
public class InvoiceSummaryForPropertyDueDateStatus_backgroundPrepareInvoiceDocs extends
        InvoiceSummaryForPropertyDueDateStatus_backgroundPrepareAbstract {

    public InvoiceSummaryForPropertyDueDateStatus_backgroundPrepareInvoiceDocs(final InvoiceSummaryForPropertyDueDateStatus invoiceSummary) {
        super(invoiceSummary, DocumentTypeData.INVOICE);
    }

    @Override
    Predicate<Invoice> filter() {
        // can only create invoice notes that have been invoiced (no longer changeable)
        return Invoice.Predicates.noLongerChangeable();
    }

}
