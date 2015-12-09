package Models;

/**
 *
 * @author Christian Neumann
 */
public class BrightBox {
    
    private int id;
    private String name;
    private String description;
    private String identifier;

    public BrightBox(int id, String name, String description, String identifier) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.identifier = identifier;
    }

    public BrightBox(int id) {
        this.id = id;
    }

    public BrightBox() {
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    
    
}
