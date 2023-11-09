package de.essagl.tuya.aircal.adapter.web.mvc;

import de.essagl.tuya.aircal.adapter.web.mvc.model.SetupData;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Properties;

@Controller
@RequestMapping()
public class SetupController {

    @GetMapping({"/url"})
    public String url(Model model) throws IOException {
        SetupData setupdata = getSetupDataFromFile();
        model.addAttribute("setupData", setupdata);
        return "url";
    }
    @GetMapping({"/setup"})
    public String main(Model model) throws IOException {
        SetupData setupdata = getSetupDataFromFile();
        model.addAttribute("setupData", setupdata);
        return "setup";
    }


    @PostMapping({"/setup"})
    public String save(SetupData setupData, Model model) throws IOException {
        storeSetupDataInFile(setupData);
        model.addAttribute("setupData", setupData);
        return "setup";
    }

    public static SetupData getSetupDataFromFile() throws IOException {
        FileSystemResource fileSystemResource = new FileSystemResource("/home/pi/heatlogic/application.properties");
        Properties properties = new Properties();
        properties.load(fileSystemResource.getInputStream());
        SetupData setupData = new SetupData();
        setupData.setConnectorAccessId(properties.getProperty("connector.ak"));
        setupData.setConnectorSecret(properties.getProperty("connector.sk"));
        setupData.setConnectorRegion(properties.getProperty("connector.region"));
        setupData.setHeatPumpControlPanelVersion(properties.getProperty("heatPumpControlPanelVersion"));
        setupData.setHeatingLogicMode(properties.getProperty("heatingLogicMode"));
        setupData.setHeatingLogicRunningRate(Integer.parseInt(properties.getProperty("heatingLogicRunningRate")));
        setupData.setIndoorDefaultSetTemperature(Double.parseDouble(properties.getProperty("indoorDefaultSetTemperature")));
        setupData.setHeatingFlowTemperature(Double.parseDouble(properties.getProperty("heatingFlowTemperature")));
        setupData.setStandbyFlowTemperature(Double.parseDouble(properties.getProperty("standbyFlowTemperature")));
        setupData.setIpAddress(properties.getProperty("ipAddress"));
        setupData.setHeatPumpDeviceId(properties.getProperty("heatPumpDeviceId"));
        setupData.setIndoorThermometerDeviceId(properties.getProperty("indoorThermometerDeviceId"));
        setupData.setIpAddress(getLocalHostLANAddress().getHostAddress());
        return setupData;
    }

    private static void storeSetupDataInFile(SetupData setupData) throws IOException {
        Properties properties = new Properties();
        properties.setProperty("connector.ak", setupData.getConnectorAccessId());
        properties.setProperty("connector.sk", setupData.getConnectorSecret());
        properties.setProperty("connector.region", setupData.getConnectorRegion());
        properties.setProperty("heatPumpControlPanelVersion", setupData.getHeatPumpControlPanelVersion());
        properties.setProperty("heatingLogicMode", setupData.getHeatingLogicMode());
        properties.setProperty("heatingLogicRunningRate", String.valueOf(setupData.getHeatingLogicRunningRate()));
        properties.setProperty("indoorDefaultSetTemperature", String.valueOf(setupData.getIndoorDefaultSetTemperature()));
        properties.setProperty("heatingFlowTemperature", String.valueOf(setupData.getHeatingFlowTemperature()));
        properties.setProperty("standbyFlowTemperature", String.valueOf(setupData.getStandbyFlowTemperature()));
        properties.setProperty("heatPumpDeviceId", setupData.getHeatPumpDeviceId());
        properties.setProperty("indoorThermometerDeviceId", setupData.getIndoorThermometerDeviceId());
        FileSystemResource fileSystemResource = new FileSystemResource("/home/pi/heatlogic/application.properties");
        properties.store(fileSystemResource.getOutputStream(), "HeatLogic Configuration");
        setupData.setHint("Restart for the changes to take effect");
    }

    /**
     * Returns an <code>InetAddress</code> object encapsulating what is most likely the machine's LAN IP address.
     * <p/>
     * This method is intended for use as a replacement of JDK method <code>InetAddress.getLocalHost</code>, because
     * that method is ambiguous on Linux systems. Linux systems enumerate the loopback network interface the same
     * way as regular LAN network interfaces, but the JDK <code>InetAddress.getLocalHost</code> method does not
     * specify the algorithm used to select the address returned under such circumstances, and will often return the
     * loopback address, which is not valid for network communication. Details
     * <a href="http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4665037">here</a>.
     * <p/>
     * This method will scan all IP addresses on all network interfaces on the host machine to determine the IP address
     * most likely to be the machine's LAN address. If the machine has multiple IP addresses, this method will prefer
     * a site-local IP address (e.g. 192.168.x.x or 10.10.x.x, usually IPv4) if the machine has one (and will return the
     * first site-local address if the machine has more than one), but if the machine does not hold a site-local
     * address, this method will return simply the first non-loopback address found (IPv4 or IPv6).
     * <p/>
     * If this method cannot find a non-loopback address using this selection algorithm, it will fall back to
     * calling and returning the result of JDK method <code>InetAddress.getLocalHost</code>.
     * <p/>
     *
     * @throws UnknownHostException If the LAN address of the machine cannot be found.
     */
    private static InetAddress getLocalHostLANAddress() throws UnknownHostException {
        try {
            InetAddress candidateAddress = null;
            // Iterate all NICs (network interface cards)...
            for (Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements(); ) {
                NetworkInterface iface = ifaces.nextElement();
                // Iterate all IP addresses assigned to each card...
                for (Enumeration<InetAddress> inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements(); ) {
                    InetAddress inetAddr = inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {

                        if (inetAddr.isSiteLocalAddress()) {
                            // Found non-loopback site-local address. Return it immediately...
                            return inetAddr;
                        } else if (candidateAddress == null) {
                            // Found non-loopback address, but not necessarily site-local.
                            // Store it as a candidate to be returned if site-local address is not subsequently found...
                            candidateAddress = inetAddr;
                            // Note that we don't repeatedly assign non-loopback non-site-local addresses as candidates,
                            // only the first. For subsequent iterations, candidate will be non-null.
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                // We did not find a site-local address, but we found some other non-loopback address.
                // Server might have a non-site-local address assigned to its NIC (or it might be running
                // IPv6 which deprecates the "site-local" concept).
                // Return this non-loopback candidate address...
                return candidateAddress;
            }
            // At this point, we did not find a non-loopback address.
            // Fall back to returning whatever InetAddress.getLocalHost() returns...
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress == null) {
                throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
            }
            return jdkSuppliedAddress;
        } catch (Exception e) {
            UnknownHostException unknownHostException = new UnknownHostException("Failed to determine LAN address: " + e);
            unknownHostException.initCause(e);
            throw unknownHostException;
        }
    }
}
