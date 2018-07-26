package org.estatio.module.capex.imports;

import java.util.List;

import javax.inject.Inject;

import com.google.common.collect.Lists;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.excel.dom.ExcelFixture;
import org.isisaddons.module.excel.dom.ExcelFixture2;
import org.isisaddons.module.excel.dom.ExcelFixtureRowHandler;
import org.isisaddons.module.excel.dom.FixtureAwareRowHandler;

import org.estatio.module.asset.dom.Property;
import org.estatio.module.asset.dom.PropertyRepository;
import org.estatio.module.base.dom.Importable;
import org.estatio.module.capex.dom.invoice.IncomingInvoiceType;
import org.estatio.module.capex.dom.order.Order;
import org.estatio.module.capex.dom.order.OrderRepository;
import org.estatio.module.capex.dom.order.approval.OrderApprovalState;
import org.estatio.module.party.dom.Organisation;
import org.estatio.module.party.dom.PartyRepository;

import lombok.Getter;
import lombok.Setter;

@DomainObject(
        nature = Nature.VIEW_MODEL,
        objectType = "org.estatio.app.services.budget.OrderImport"
)
public class OrderImport implements FixtureAwareRowHandler<OrderImport>, ExcelFixtureRowHandler, Importable {

    public String title() {
        return "order import";
    }

    public OrderImport() {
    }

    public OrderImport(
            final String propertyReference,
            final String orderType,
            final String orderNumber,
            final String sellerOrderReference,
            final LocalDate entryDate,
            final LocalDate orderDate,
            final String sellerReference,
            final String buyerReference,
            final String atPath,
            final String approvalStateIfAny,
            final String approvedBy,
            final LocalDate approvedOn
    ) {
        this();
        this.propertyReference = propertyReference;
        this.orderType = orderType;
        this.orderNumber = orderNumber;
        this.sellerOrderReference = sellerOrderReference;
        this.entryDate = entryDate;
        this.orderDate = orderDate;
        this.sellerReference = sellerReference;
        this.buyerReference = buyerReference;
        this.atPath = atPath;
        this.approvalStateIfAny = approvalStateIfAny;
        this.approvedBy = approvedBy;
        this.approvedOn = approvedOn;
    }

    // order
    @Getter @Setter
    private String propertyReference;
    @Getter @Setter
    private String orderType;
    @Getter @Setter
    private String orderNumber;
    @Getter @Setter
    private String sellerOrderReference;
    @Getter @Setter
    private LocalDate entryDate;
    @Getter @Setter
    private LocalDate orderDate;
    @Getter @Setter
    private String sellerReference;
    @Getter @Setter
    private String buyerReference;
    @Getter @Setter
    private String atPath;
    @Getter @Setter
    private String approvalStateIfAny;
    @Getter @Setter
    private String approvedBy;
    @Getter @Setter
    private LocalDate approvedOn;

    /**
     * To allow for usage within fixture scripts also.
     */
    @Setter
    private FixtureScript.ExecutionContext executionContext;

    /**
     * To allow for usage within fixture scripts also.
     */
    @Setter
    private ExcelFixture2 excelFixture2;

    @Override
    @Programmatic
    public void handleRow(final OrderImport previousRow) {
        importData(previousRow);
    }

    @Override
    public List<Object> handleRow(final FixtureScript.ExecutionContext executionContext, final ExcelFixture excelFixture, final Object previousRow) {
        return importData(previousRow);

    }

    @Override
    @Programmatic
    public List<Object> importData(Object previousRow) {
        Property property = propertyRepository.findPropertyByReference(getPropertyReference());
        IncomingInvoiceType orderType = getOrderType()!=null ? IncomingInvoiceType.valueOf(getOrderType()) : null;
        Organisation seller = (Organisation) partyRepository.findPartyByReference(getSellerReference());
        Organisation buyer = (Organisation) partyRepository.findPartyByReference(getBuyerReference());
        OrderApprovalState approvalState = getApprovalStateIfAny()!=null ? OrderApprovalState.valueOf(getApprovalStateIfAny()) : null;
        Order order = orderRepository.upsert(property, orderType, getOrderNumber(), getSellerOrderReference(), getEntryDate(), getOrderDate(), seller, buyer, "/ITA", approvalState);
        order.setApprovedBy(getApprovedBy());
        order.setApprovedOn(getApprovedOn());
        return Lists.newArrayList(order);
    }

    @Inject PartyRepository partyRepository;

    @Inject PropertyRepository propertyRepository;

    @Inject OrderRepository orderRepository;

}
