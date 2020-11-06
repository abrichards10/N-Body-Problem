
//Angela Richards: Assignment1

import java.util.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.Timer;

// so I can use random etc...
import java.io.File;
// otherwise it won't let us read the input
import java.io.FileNotFoundException;

public class NBodyProblem extends JPanel implements ActionListener{

    // Gravitational constant: G
    private static final double G = 6.67e-11;

    private String name;
    private double mass;
    private int initial_x;
    private int initial_y;
    private double x_velocity;
    private double y_velocity;
    private int size;
    private Color color;
    private double x_force, y_force;

    // Constructor
    public NBodyProblem(String name, String mass, String initial_x, String initial_y, String x_velocity, String y_velocity, String size) {
        this.name = name;
        this.mass = Double.parseDouble(mass);
        this.initial_x = Integer.parseInt(initial_x);
        this.initial_y = Integer.parseInt(initial_y);
        this.x_velocity = Double.parseDouble(x_velocity);
        this.y_velocity = Double.parseDouble(y_velocity);
        this.size = Integer.parseInt(size.substring(1));
        Random rand = new Random();
        color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

    // create a new celestial body called list
    private NBody<NBodyProblem> list;
    private double scale;

    // take in the list and size of the list and assign each value
    public NBodyProblem(NBody<NBodyProblem> new_list, double new_scale) {
        list = new_list;
        scale = new_scale;
    }

    //get functions
    public double get_mass() {
        return mass;
    }

    public int getInitial_x() {
        return initial_x;
    }

    public int getInitial_y() {
        return initial_y;
    }

    public double getX_velocity() {
        return x_velocity;
    }

    public double getY_velocity() {
        return y_velocity;
    }

    public int get_size() {
        return size;
    }

    public Color getColor() {
        return color;
    }

    // calculates the force using the new list and size
    public void force(NBodyProblem b, double scale) {
        NBodyProblem a = this;

        // x and y distance
        double x_distance = b.initial_x - a.initial_x;
        double y_distance = b.initial_y - a.initial_y;

        // calculate the total distance
        double total_distance = Math.sqrt((x_distance * x_distance) + (y_distance * y_distance));
        // calculate the force using the gravitational constant
        double force = (G * a.mass * b.mass) / ((total_distance * total_distance) / scale);

        //set x and y forces in both directions
        a.x_force += force * x_distance / total_distance;
        a.y_force += force * y_distance / total_distance;
    }

    // resets the force to 0
    public void resetForce() {
        x_force = 0.0;
        y_force = 0.0;
    }

    // updates the position of the bodies
    public void updatePosition() {
        x_velocity += x_force / mass;
        y_velocity += y_velocity / mass;

        initial_x += x_velocity;
        initial_y += y_velocity;
    }

    // Weee're Painting the Celestial Bodies Reeed – Alice in Wonderland probably
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // borrowed this mad lad from the tutorial
        Timer tm = new Timer(5, this);

        // Here, we render the celestial bodies
        // we use the get functions from NBody to get the start x & y positions,
        for (int i = 0; i < list.get_size(); i++) {
            g.setColor(list.get(i).getColor());
            g.fillOval(list.get(i).getInitial_x(), list.get(i).getInitial_y(), list.get(i).get_size(), list.get(i).get_size());
        }
        tm.start();
    }

    //uses updatePosition to change position (Req 4) and reset the force impacting the celestial bodies
    public void update_Bodies() {
        int i;
        for (i = 0; i < list.get_size() - 1; i++) {
            list.get(i).force(list.get(i + 1), scale);
            list.get(i).updatePosition();
            list.get(i).resetForce();
        }
        if (list.get_size() > 1) {
            list.get(i).force(list.get(i - 1), scale);
            list.get(i).updatePosition();
            list.get(i).resetForce();
        }
    }

    // override so we don't have to make the class abstract
    // if an action is performed, update the body positions and forces, and repaint each one
    @Override
    public void actionPerformed(ActionEvent e) {
        update_Bodies();
        repaint();
    }

    public static void main(String[] args) {
        // create a temporary NBody that is empty
        NBody<NBodyProblem> temp = null;
        // create a temporary scale set at 0
        double scale_temp = 0;
        // create a new file that starts with reading the first line (0)
        File file = new File(args[0]);

        // while ignoring the error where it claims we don't have a file,
        try {
            Scanner sc = new Scanner(file);
            // scan the file and create a string that is a copy of the next line
            String listType = sc.nextLine();

            // if the string is equal to the type at the Top of nbody_input.txt,
            // (which is either ArrayList or LinkedList)
            // We create a new list based on what we find at the top
            if (listType.equals("ArrayList")) {
                temp = new ArrayList<>();
            } else if (listType.equals("LinkedList")) {
                temp = new LinkedList<>();
            } else {
                // otherwise we didn't find a match
                System.out.println("Invalid list type");
            }
            // assign the temp to the next line of the file
            scale_temp = Double.parseDouble(sc.nextLine());
            // Scan for a comma, which indicates a new bout of information
            sc.useDelimiter(",");
            // while the scanner still has more information
            while (sc.hasNext()) {
                // test the validity of the temp to see if it's not null
                assert temp != null;
                // then add the information given in the next line of the text to render a body
                temp.add(new NBodyProblem(sc.next(), sc.next(), sc.next(), sc.next(), sc.next(), sc.next(), sc.nextLine()));
            }
            // close the scan
            sc.close();
            //You have no idea how much i needed to throw this exception
            // seriously, this was the only error coming up for hours because I implemented it wrong
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        // create a new body with the temporary NBody and scale
        NBodyProblem nBody = new NBodyProblem(temp, scale_temp);

        // Yay more tutorial stuff
        // Render the celestial bodies
        JFrame jf = new JFrame();

        jf.setTitle("N-Body Problem");
        jf.setSize(768, 768); // specified window size
        jf.add(nBody);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

/* TUTORIAL NOTES YOU CAN STEAL AT ANY TIME DARLIN

    // x represents the horizontal pos
    // velX represents the speed
    int x = 0, velX = 2;

    // method that makes shapes
    public void paintComponent(Graphics g) {
        // displays properly
        super.paintComponent(g);


        g.setColor(Color.RED);
        g.drawRect(100, 10, 30, 40);
        g.fillRect(x, 10, 20, 10);

        tm.start();

        g.setColor(Color.BLUE);
        g.drawOval(100, 100, 50, 50);
        g.fillOval(100, 200, 50, 50);

        g.setColor(Color.BLACK);
        g.drawLine(10, 10, 30, 70);
    }

    public void actionPerformed(ActionEvent e) {
        // move rectangle from 0 to the right, increasing by 2 each time
        x = x + velX;
        if (x < 0 || x > 550) //550 = window size - width of rectangle
            velX = -velX;

        repaint(); //repaints the rectangle every 5 milliseconds
    }

 */

