/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appd.a2cm.pidManager;

/**
 *
 * @author anwar
 */
public class MachineAgentProcess {
    
    private String osName;
    
    public MachineAgentProcess() {
        osName = System.getProperty("os.name");
        System.out.println(osName);
    }
    
    
}
