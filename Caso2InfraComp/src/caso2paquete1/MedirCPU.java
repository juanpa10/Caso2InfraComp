package caso2paquete1;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.NumberFormat;

import com.sun.management.OperatingSystemMXBean;

public class MedirCPU {

	
	 private Runtime runtime = Runtime.getRuntime();

	   
	   
	 
	    public String OSname() {
	        return System.getProperty("os.name");
	    }

	    public String OSversion() {
	        return System.getProperty("os.version");
	    }

	    public String OsArch() {
	        return System.getProperty("os.arch");
	    }

	    public long totalMem() {
	        return Runtime.getRuntime().totalMemory();
	    }

	    public long usedMem() {
	        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
	    }

	    public String MemInfo() 
	    {
	    	OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
	        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
	        int availableProcessors = operatingSystemMXBean.getAvailableProcessors();
	        long prevUpTime = runtimeMXBean.getUptime();
	        long prevProcessCpuTime = operatingSystemMXBean.getProcessCpuTime();
	        double cpuUsage;
	        try
	        {
	            Thread.sleep(10000);
	        }
	        catch (Exception ignored) { }

	        operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
	        long upTime = runtimeMXBean.getUptime();
	        long processCpuTime = operatingSystemMXBean.getProcessCpuTime();
	        long elapsedCpu = processCpuTime - prevProcessCpuTime;
	        long elapsedTime = upTime - prevUpTime;

	        cpuUsage = Math.min(99F, elapsedCpu / (elapsedTime * 10000F * availableProcessors));
	       return cpuUsage+"";

	    }
	    
	    
	    public static void main(String[] args) 
	    {
	    	MedirCPU n= new  MedirCPU();
	    	System.out.println(n.MemInfo());
		}
}
