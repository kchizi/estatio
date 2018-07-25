package org.estatio.module.capex.imports;

import java.math.BigDecimal;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.schema.utils.jaxbadapters.JodaLocalDateStringAdapter;

import org.incode.module.country.dom.impl.CountryRepository;

import org.estatio.module.capex.dom.invoice.IncomingInvoiceType;
import org.estatio.module.capex.dom.order.OrderRepository;
import org.estatio.module.capex.dom.order.approval.OrderApprovalState;
import org.estatio.module.party.dom.Organisation;
import org.estatio.module.party.dom.OrganisationRepository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// TODO: is there a reason to use XML VM?
@XmlRootElement(name = "orderProjectLine")
@XmlType(
        propOrder = {
                "sheetName",
                "rowNumber",
                "orderNumber",
                "centro",
                "progressivoCentro",
                "commessa",
                "workType",
                "integrazione",
                "data",
                "oggetto",
                "sellerName",
                "sellerReference",
                "importoNettoIVA",
                "cassaProfess",
                "importoTotale",
                "iva",
                "totaleConIVA",
                "autorizzato",
                "note"
        }
)
@DomainObject(
        objectType = "orders.OrderProjectLine"
)
@XmlAccessorType(XmlAccessType.FIELD)
@AllArgsConstructor
public class OrderProjectLine {

    public String title() {
        return "Order - Project Import Line";
    }

    public OrderProjectLine() {}

    @XmlElement(required = false) @Nullable
    @Getter @Setter
    @MemberOrder(sequence = "1")
    private String sheetName;

    @XmlElement(required = false) @Nullable
    @Getter @Setter
    @MemberOrder(sequence = "2")
    private Integer rowNumber;

    @XmlElement(required = false) @Nullable
    @Getter @Setter
    @MemberOrder(sequence = "3")
    private String orderNumber;

    @XmlElement(required = false) @Nullable
    @Getter @Setter
    @MemberOrder(sequence = "4")
    private String centro;

    @XmlElement(required = false) @Nullable
    @Getter @Setter
    @MemberOrder(sequence = "5")
    private Integer progressivoCentro;

    @XmlElement(required = false) @Nullable
    @Getter @Setter
    @MemberOrder(sequence = "6")
    private Integer commessa;

    @XmlElement(required = false) @Nullable
    @Getter @Setter
    @MemberOrder(sequence = "7")
    private Integer workType;

    @XmlElement(required = false) @Nullable
    @Getter @Setter
    @MemberOrder(sequence = "8")
    private Integer integrazione;

    @XmlElement(required = false) @Nullable
    @Getter @Setter
    @MemberOrder(sequence = "9")
    @XmlJavaTypeAdapter(JodaLocalDateStringAdapter.ForJaxb.class)
    private LocalDate data;

    @XmlElement(required = false) @Nullable
    @Getter @Setter
    @MemberOrder(sequence = "10")
    private String oggetto;

    @XmlElement(required = false) @Nullable
    @Getter @Setter
    @MemberOrder(sequence = "11")
    private String sellerName;

    @XmlElement(required = false) @Nullable
    @Getter @Setter
    @MemberOrder(sequence = "12")
    private String sellerReference;

    @XmlElement(required = false) @Nullable
    @Getter @Setter
    @MemberOrder(sequence = "13")
    @Column(scale = 2)
    private BigDecimal importoNettoIVA;

    @XmlElement(required = false) @Nullable
    @Getter @Setter
    @MemberOrder(sequence = "14")
    @Column(scale = 2)
    private BigDecimal cassaProfess;

    @XmlElement(required = false) @Nullable
    @Getter @Setter
    @MemberOrder(sequence = "15")
    @Column(scale = 2)
    private BigDecimal importoTotale;

    @XmlElement(required = false) @Nullable
    @Getter @Setter
    @MemberOrder(sequence = "16")
    @Column(scale = 2)
    private BigDecimal iva;

    @XmlElement(required = false) @Nullable
    @Getter @Setter
    @MemberOrder(sequence = "17")
    @Column(scale = 2)
    private BigDecimal totaleConIVA;

    @XmlElement(required = false) @Nullable
    @Getter @Setter
    @MemberOrder(sequence = "18")
    private String autorizzato;

    @XmlElement(required = false) @Nullable
    @Getter @Setter
    @MemberOrder(sequence = "19")
    private String note;

    /**
     * Using a mixin so can continue to use lombok's @AllArgsConstructor
     * (else the additional injected services required confuse things)
     *
     */
    @Mixin(method="act")
    public static class _apply {
        private static final Logger LOG = LoggerFactory.getLogger(OrderProjectLine.class);
        private final OrderProjectLine line;
        public _apply(final OrderProjectLine line) {
            this.line = line;
        }

        @Action()
        @ActionLayout(contributed= Contributed.AS_ACTION)
        public OrderProjectLine act() {
            Organisation seller = null;
            if (line.getSellerReference()!=null) {
                seller = organisationRepository.findOrCreateOrganisation(line.getSellerReference(), false, line.getSellerName(), countryRepository.findCountry("ITA"));
            }
            orderRepository.upsert(null, IncomingInvoiceType.CAPEX, line.getOrderNumber(), null, null, null, seller, null, "/ITA", OrderApprovalState.APPROVED);
            return line;
        }


        @Inject OrderRepository orderRepository;

        @Inject OrganisationRepository organisationRepository;

        @Inject CountryRepository countryRepository;
    }


}

