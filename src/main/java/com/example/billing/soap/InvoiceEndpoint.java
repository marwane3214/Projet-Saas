package com.example.billing.soap;

import com.example.billing.application.dto.CreateInvoiceRequest;
import com.example.billing.application.dto.InvoiceDTO;
import com.example.billing.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Endpoint
@RequiredArgsConstructor
@Slf4j
public class InvoiceEndpoint {
    
    private static final String NAMESPACE_URI = "http://example.com/billing/soap";
    
    private final InvoiceService invoiceService;
    private final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    
    {
        documentBuilderFactory.setNamespaceAware(true);
    }
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createInvoiceRequest")
    @ResponsePayload
    public Element createInvoice(@RequestPayload Element request) {
        try {
            log.info("SOAP: Creating invoice");
            
            Long customerId = Long.parseLong(getElementValue(request, "customerId"));
            Long subscriptionId = Long.parseLong(getElementValue(request, "subscriptionId"));
            BigDecimal amount = new BigDecimal(getElementValue(request, "amount"));
            String currency = getElementValue(request, "currency");
            if (currency == null || currency.isEmpty()) {
                currency = "USD";
            }
            LocalDate dueDate = LocalDate.parse(getElementValue(request, "dueDate"));
            String notes = getElementValue(request, "notes");
            String idempotencyKey = getElementValue(request, "idempotencyKey");
            
            com.example.billing.application.dto.CreateInvoiceRequest dto = 
                com.example.billing.application.dto.CreateInvoiceRequest.builder()
                    .customerId(customerId)
                    .subscriptionId(subscriptionId)
                    .amount(amount)
                    .currency(currency)
                    .dueDate(dueDate)
                    .notes(notes)
                    .idempotencyKey(idempotencyKey)
                    .build();
            
            InvoiceDTO invoice = invoiceService.createInvoice(dto);
            
            return createInvoiceResponseElement(invoice, "createInvoiceResponse");
        } catch (Exception e) {
            log.error("Error creating invoice via SOAP", e);
            throw new RuntimeException("Error creating invoice: " + e.getMessage(), e);
        }
    }
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInvoiceRequest")
    @ResponsePayload
    public Element getInvoice(@RequestPayload Element request) {
        try {
            log.info("SOAP: Getting invoice");
            
            Long id = Long.parseLong(getElementValue(request, "id"));
            InvoiceDTO invoice = invoiceService.getInvoiceById(id);
            
            return createInvoiceResponseElement(invoice, "getInvoiceResponse");
        } catch (Exception e) {
            log.error("Error getting invoice via SOAP", e);
            throw new RuntimeException("Error getting invoice: " + e.getMessage(), e);
        }
    }
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInvoicesByCustomerRequest")
    @ResponsePayload
    public Element getInvoicesByCustomer(@RequestPayload Element request) {
        try {
            log.info("SOAP: Getting invoices by customer");
            
            Long customerId = Long.parseLong(getElementValue(request, "customerId"));
            List<InvoiceDTO> invoices = invoiceService.getInvoicesByCustomer(customerId);
            
            return createInvoicesListResponseElement(invoices);
        } catch (Exception e) {
            log.error("Error getting invoices by customer via SOAP", e);
            throw new RuntimeException("Error getting invoices: " + e.getMessage(), e);
        }
    }
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "markInvoiceAsPaidRequest")
    @ResponsePayload
    public Element markInvoiceAsPaid(@RequestPayload Element request) {
        try {
            log.info("SOAP: Marking invoice as paid");
            
            Long id = Long.parseLong(getElementValue(request, "id"));
            InvoiceDTO invoice = invoiceService.markInvoiceAsPaid(id);
            
            return createInvoiceResponseElement(invoice, "markInvoiceAsPaidResponse");
        } catch (Exception e) {
            log.error("Error marking invoice as paid via SOAP", e);
            throw new RuntimeException("Error marking invoice as paid: " + e.getMessage(), e);
        }
    }
    
    private String getElementValue(Element parent, String tagName) {
        try {
            org.w3c.dom.NodeList nodes = parent.getElementsByTagNameNS("*", tagName);
            if (nodes.getLength() > 0) {
                return nodes.item(0).getTextContent();
            }
            // Try without namespace
            nodes = parent.getElementsByTagName(tagName);
            if (nodes.getLength() > 0) {
                return nodes.item(0).getTextContent();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    
    private Element createInvoiceResponseElement(InvoiceDTO invoice, String responseName) {
        try {
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            Document doc = builder.newDocument();
            
            Element response = doc.createElementNS(NAMESPACE_URI, responseName);
            doc.appendChild(response);
            
            Element invoiceElement = doc.createElementNS(NAMESPACE_URI, "invoice");
            response.appendChild(invoiceElement);
            
            addElement(doc, invoiceElement, "id", invoice.getId() != null ? invoice.getId().toString() : "");
            addElement(doc, invoiceElement, "number", invoice.getNumber());
            addElement(doc, invoiceElement, "customerId", invoice.getCustomerId().toString());
            addElement(doc, invoiceElement, "subscriptionId", invoice.getSubscriptionId().toString());
            addElement(doc, invoiceElement, "amount", invoice.getAmount().toString());
            addElement(doc, invoiceElement, "currency", invoice.getCurrency());
            addElement(doc, invoiceElement, "status", invoice.getStatus() != null ? invoice.getStatus().name() : "DRAFT");
            addElement(doc, invoiceElement, "dueDate", invoice.getDueDate().toString());
            if (invoice.getIssuedAt() != null) {
                addElement(doc, invoiceElement, "issuedAt", invoice.getIssuedAt().toString());
            }
            if (invoice.getNotes() != null) {
                addElement(doc, invoiceElement, "notes", invoice.getNotes());
            }
            
            return response;
        } catch (Exception e) {
            log.error("Error creating response element", e);
            throw new RuntimeException("Error creating SOAP response", e);
        }
    }
    
    private Element createInvoicesListResponseElement(List<InvoiceDTO> invoices) {
        try {
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            Document doc = builder.newDocument();
            
            Element response = doc.createElementNS(NAMESPACE_URI, "getInvoicesByCustomerResponse");
            doc.appendChild(response);
            
            Element invoicesElement = doc.createElementNS(NAMESPACE_URI, "invoices");
            response.appendChild(invoicesElement);
            
            for (InvoiceDTO invoice : invoices) {
                Element invoiceElement = doc.createElementNS(NAMESPACE_URI, "invoice");
                invoicesElement.appendChild(invoiceElement);
                
                addElement(doc, invoiceElement, "id", invoice.getId() != null ? invoice.getId().toString() : "");
                addElement(doc, invoiceElement, "number", invoice.getNumber());
                addElement(doc, invoiceElement, "customerId", invoice.getCustomerId().toString());
                addElement(doc, invoiceElement, "subscriptionId", invoice.getSubscriptionId().toString());
                addElement(doc, invoiceElement, "amount", invoice.getAmount().toString());
                addElement(doc, invoiceElement, "currency", invoice.getCurrency());
                addElement(doc, invoiceElement, "status", invoice.getStatus() != null ? invoice.getStatus().name() : "DRAFT");
                addElement(doc, invoiceElement, "dueDate", invoice.getDueDate().toString());
            }
            
            return response;
        } catch (Exception e) {
            log.error("Error creating invoices list response", e);
            throw new RuntimeException("Error creating SOAP response", e);
        }
    }
    
    private void addElement(Document doc, Element parent, String name, String value) {
        Element element = doc.createElementNS(NAMESPACE_URI, name);
        element.setTextContent(value != null ? value : "");
        parent.appendChild(element);
    }
}
