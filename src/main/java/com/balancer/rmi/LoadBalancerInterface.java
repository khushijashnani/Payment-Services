package com.balancer.rmi;

import com.model.AcquirerTransaction;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LoadBalancerInterface extends Remote {
    public AcquirerTransaction processMerchantTransaction(AcquirerTransaction acquirerTransaction) throws Exception;
}
