package com.balancer.rmi;

import com.acquirer.rmi.AcquirerProcessInterface;
import com.model.AcquirerTransaction;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LoadBalancerImpl implements LoadBalancerInterface{

    int nodeCount = 3;
    Integer requestCount = -1;
    String[] acquirers = new String[]{"acquirer1", "acquirer2", "acquirer1"};


    @Override
    public AcquirerTransaction processMerchantTransaction(AcquirerTransaction acquirerTransaction) throws Exception {
        synchronized (requestCount){
            requestCount++;
        }
        System.out.println(requestCount);
        Registry registry = LocateRegistry.getRegistry(2000);
        AcquirerProcessInterface acquirerProcess = (AcquirerProcessInterface) registry.lookup(acquirers[requestCount%nodeCount]);
        System.out.println("Re directing the below request with ID ="+ acquirerTransaction.getId() + " to server ="+ acquirers[requestCount%nodeCount]);
        System.out.println(acquirerProcess.processMerchantTransaction(acquirerTransaction));
        return acquirerTransaction;
    }
}
