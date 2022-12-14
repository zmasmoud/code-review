import java.io.*;
import java.util.ArrayList;

public class OrderService {
    ArrayList<Order> orders = new ArrayList<Order>();

    public OrderService() {
        try {
            Order foo = readOrderFromTestFile(1);
            if (foo.isValid()) {
                orders.add(foo);
            }
            Order bar = readOrderFromTestFile(2);
            if (bar.isValid()) {
                orders.add(bar);
            }
            Order foobar = readOrderFromBinaryFile(3);
            if (foobar.isValid()) {
                orders.add(bar);
            }
        } catch (FileNotFoundException e) {
            // Orders file is not found
        } catch (IOException e) {
            // Other I/O error
        } catch (ClassNotFoundException e) {
            // Class Not found exception
        }
    }

    public Order findOrderById(Integer id) {
        Order o = null;
        // Loop on each order to find the expected id
        for (Order i : orders) {
            if (i.getId() == id) {
                o = i;
            }
        }
        return o;
    }

    public Order readOrderFromTestFile(Integer id) throws FileNotFoundException, IOException {
        Order order = null;
        String content;
        File file = new File("order-" + id + ".txt");
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
            order = new Order(id, content);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return order;
    }

    public Order readOrderFromBinaryFile(int id) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("order-" + id + ".bin"));
        return (Order) ois.readObject();
    }

    private static class Order implements Serializable {
        private Integer id;
        private String description;

        private Order() {

        }

        private Order(Integer id, String description) {
            this.id = id;
            this.description = description;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isValid() {
            description = description.replaceAll("\\t", "\\s");
            return description.length() > 0;
        }
    }
}