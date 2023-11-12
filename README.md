```
  @Lock(LockModeType.PESSIMISTIC_READ)
  @Query("select inv from InvoiceV2 inv WHERE inv.id = :invoiceId")
  InvoiceV2 findInvoiceByIdPessimisticLock(@Param("invoiceId") String invoiceId);

  public InvoiceWriteResult saveInvoice(InvoiceV2 invoice, Collection<PaymentRequest> paymentRequestList) {
           TransactionStatus transaction =
            transactionManager.getTransaction(getDefaultTransactionDefinition(0, 2));
    InvoiceWriteResult invoiceWriteResult = new InvoiceWriteResult();
    String invoiceId = invoice.getId();
    try {
      invoice.setLastUpdatedByUserId(getCurrentUserId());
      invoice.setModifiedTime(System.currentTimeMillis());
      invoice = invoiceRepo.save(invoice);
      if(TCollectionUtils.isNotEmpty(paymentRequestList))
        paymentRequestRepo.saveAll(paymentRequestList);
      transactionManager.commit(transaction);
      invoice = invoiceRepo.findById(invoice.getId()).orElse(null);
      if(invoice == null) {
        log.error(" invoice is null after successful payment");
      }
      invoiceWriteResult.setInvoiceSaved(true);
    }
    catch (Exception ex) {
      log.info("exception while saving invoice {}", ex);
      if(!transaction.isCompleted())
        transactionManager.rollback(transaction);
      invoiceWriteResult.setInvoiceSaved(false);
    }
    invoice = invoiceRepo.findById(invoiceId).orElse(null);
    invoiceWriteResult.setInvoice(invoice);
    return invoiceWriteResult;
  }

  @Transactional("tenantTransactionManager")
  public CostChanges updateOneInvoice(String invoiceId) throws IOException {
    InvoiceV2 invoice = invoiceRepo.findInvoiceByIdPessimisticLock(invoiceId);

```
