package fon.silab.web.an.ainmusicshop.service.impl;

import java.util.*;
import com.paypal.api.payments.*;
import com.paypal.base.rest.*;
import fon.silab.web.an.ainmusicshop.dto.OrderDto;
import fon.silab.web.an.ainmusicshop.dto.OrderItemDto;

public class PaymentServices {

    private static final String CLIENT_ID = "AayLKLcbgIzHkQEvsycJh1r5QVzsBzDXLYngddz9EIN2AcY3w5U0SwMg46QX0Y1JiGOSM3rc_pOppxvl";
    private static final String CLIENT_SECRET = "EJg-bQK8mHH07L1-wlVIUqNHN9CkeR1pDji0v3tzx_7UUHgzwiIBNd7_Hm6DFfQEWgTQclJ7mgvcYMsn";
    private static final String MODE = "sandbox";
    private OrderDto order;

    public String authorizePayment(OrderDto orderDetail)
            throws PayPalRESTException {

        Payer payer = getPayerInformation();
        RedirectUrls redirectUrls = getRedirectURLs();
        List<Transaction> listTransaction = getTransactionInformation(order);

        Payment requestPayment = new Payment();
        requestPayment.setTransactions(listTransaction);
        requestPayment.setRedirectUrls(redirectUrls);
        requestPayment.setPayer(payer);
        requestPayment.setIntent("authorize");

        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

        Payment approvedPayment = requestPayment.create(apiContext);

        return getApprovalLink(approvedPayment);

    }
    public PaymentServices(){}
    
    public PaymentServices(OrderDto orderDetail) {
        order = orderDetail;
    }

    private Payer getPayerInformation() {
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setFirstName(order.getUserDto().getFirstname())
                .setLastName(order.getUserDto().getLastname())
                .setEmail(order.getUserDto().getEmail());

        payer.setPayerInfo(payerInfo);

        return payer;

    }

    private RedirectUrls getRedirectURLs() {
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:8080/musicshop/cart");
        redirectUrls.setReturnUrl("http://localhost:8080/musicshop/PayPal/paymentComplete");

        return redirectUrls;

    }

    private List<Transaction> getTransactionInformation1(OrderDto orderDetail) {
        Details details = new Details();
        details.setShipping(orderDetail.getUserDto().getAdress());

        details.setSubtotal(10 + "");
        details.setTax(11 + "");

        Amount amount = new Amount();
        amount.setCurrency("EUR");
        int total = 0;
        for (OrderItemDto item : orderDetail.getOrderItems()) {
            total += item.getQuantity() * item.getItemPrice();
        }
        amount.setTotal(19 + "");
        amount.setDetails(details);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription(orderDetail.getOrderItems().get(0).getProduct().getProductName());

        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();

        Item item = new Item();
        item.setCurrency("USD");
        item.setName(orderDetail.getOrderItems().get(0).getProduct().getProductName());
        item.setPrice("12");
        item.setTax("13");
        item.setQuantity("1");

        items.add(item);
        itemList.setItems(items);
        transaction.setItemList(itemList);

        List<Transaction> listTransaction = new ArrayList<>();
        listTransaction.add(transaction);

        return listTransaction;

    }

    private List<Transaction> getTransactionInformation(OrderDto orderDetail) {
       

        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();

        int total = 0;
        for (OrderItemDto orderItem : orderDetail.getOrderItems()) {
            total += orderItem.getQuantity() * orderItem.getItemPrice();
        
        Item item = new Item();
        item.setCurrency("EUR");
        item.setName(orderItem.getProduct().getProductName());
        item.setPrice(orderItem.getItemPrice()+"");
        item.setTax("0");
        item.setQuantity(String.valueOf(orderItem.getQuantity()));
        items.add(item);

        }
         Details details = new Details();
        details.setShipping("0");
        details.setTax("0");

        details.setSubtotal(total+"");

        Amount amount = new Amount();
        amount.setCurrency("EUR");
        amount.setTotal(total+"");
        amount.setDetails(details);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription("opis");
        
        itemList.setItems(items);
        transaction.setItemList(itemList);

        List<Transaction> listTransaction = new ArrayList<>();
        listTransaction.add(transaction);

        return listTransaction;
    }

    private String getApprovalLink(Payment approvedPayment) {
        List<Links> links = approvedPayment.getLinks();
        String approvalLink = null;

        for (Links link : links) {
            if (link.getRel().equalsIgnoreCase("approval_url")) {
                approvalLink = link.getHref();
                break;
            }
        }

        return approvalLink;

    }
    public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
    APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
    return Payment.get(apiContext, paymentId);
}

}
