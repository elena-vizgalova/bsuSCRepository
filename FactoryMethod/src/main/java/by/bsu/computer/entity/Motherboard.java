package by.bsu.computer.entity;

import java.util.List;

/**
 *
 * @author Elena Vizgalova
 */
public class Motherboard {
    private String formFactor;
    private List<String> processors;

    /**
     * @return the formFactor
     */
    public String getFormFactor() {
        return formFactor;
    }

    /**
     * @return the processors
     */
    public List<String> getProcessors() {
        return processors;
    }

    /**
     * @param formFactor the formFactor to set
     */
    public void setFormFactor(String formFactor) {
        this.formFactor = formFactor;
    }

    /**
     * @param processors the processors to set
     */
    public void setProcessors(List<String> processors) {
        this.processors = processors;
    }
    
}
