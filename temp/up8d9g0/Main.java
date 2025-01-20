package client;

import common.Printer;
import common.Distance;
import java.util.Vector;
import java.rmi.RMISecurityManager;
import net.jini.discovery.LookupDiscovery;
import net.jini.discovery.DiscoveryListener;
import net.jini.discovery.DiscoveryEvent;
import net.jini.core.lookup.ServiceRegistrar;
import net.jini.core.lookup.ServiceTemplate;
import net.jini.core.lookup.ServiceMatches;

public class Main implements DiscoveryListener {
    protected Distance distance = null;
    protected Object distanceLock = new Object();
    protected Vector printers = new Vector();

    public static void main(String argv[]) {
        new TestPrinterDistance();
        try {
            Thread.currentThread().sleep(10000L);
        } catch (InterruptedException e) {
            // do nothing
        }
    }

    public TestPrinterDistance() {
        System.setSecurityManager(new RMISecurityManager());
        LookupDiscovery discover = null;
        try {
            discover = new LookupDiscovery(LookupDiscovery.ALL_GROUPS);
        } catch (Exception e) {
            System.err.println(e.toString());
            System.exit(1);
        }
        discover.addDiscoveryListener(this);
    }

    public void discovered(DiscoveryEvent evt) {
        ServiceRegistrar[] registrars = evt.getRegistrars();
        for (int n = 0; n < registrars.length; n++) {
            System.out.println("Service found");
            ServiceRegistrar registrar = registrars[n];
            new LookupThread(registrar).start();
        }
    }

    public void discarded(DiscoveryEvent evt) {
        // empty
    }

    class LookupThread extends Thread {
        ServiceRegistrar registrar;

        public LookupThread(ServiceRegistrar registrar) {
            this.registrar = registrar;
        }

        public void run() {
            // Lookup logic here
        }
    }
}