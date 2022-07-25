package br.com.bcp.hms.tenant;

public class ThreadContext {
    private static InheritableThreadLocal<String> currentTenant = new InheritableThreadLocal<>();

    public static void setCurrentTenant(String tenant) {
        currentTenant.set(tenant);
    }

    public static Object getCurrentTenant() {
        return currentTenant.get();
    }

    public static void clear() {
        currentTenant.set("");
    }

}
