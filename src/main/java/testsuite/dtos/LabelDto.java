package testsuite.dtos;

public record LabelDto(String label, Long id) {

    public String getLabel() {
        return label;
    }

    public Long getId() {
        return id;
    }
}
