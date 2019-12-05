package steps

def runForService(serviceName) {

    System.out.print("Hello from " + serviceName)

}

public static void main(String[] args) {
    new steps.Singular().runForService("hello")
}