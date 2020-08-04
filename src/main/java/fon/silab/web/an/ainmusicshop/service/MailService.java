/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.service;

/**
 *
 * @author lj
 */
public interface MailService {
    
    public void send(String fromAddress, String toAddress, String subject, String content) throws Exception;
}
