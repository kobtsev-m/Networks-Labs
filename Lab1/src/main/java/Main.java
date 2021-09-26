public class Main {
    private static final String DEFAULT_GROUP_ADDRESS = "224.1.1.1";
    private static final String DEFAULT_GROUP_PORT = "8001";

    public static String variableFromArgs(String[] args, int argIdx, String defaultValue) {
        if (args.length > argIdx) {
            return args[argIdx];
        } else {
            return defaultValue;
        }
    }

    public static void main(String[] args) {
        String groupAddress = variableFromArgs(args, 0, DEFAULT_GROUP_ADDRESS);
        int portAddress = Integer.parseInt(variableFromArgs(args, 1, DEFAULT_GROUP_PORT));
        Mailing mailing = new Mailing(groupAddress, portAddress);
        mailing.start();
    }
}
