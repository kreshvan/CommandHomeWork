package dynamicRuleModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import modelAndConstants.Recommendation;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class DynamicRules  {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//автоматической генерации значения идентификатора
    // при сохранении объекта в базу данных.
    private Long id;
    private String productName;
    private String productText;
    private UUID productId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    //связь между двумя сущностями (Condition и DynamicRules)
    //операции с основной сущностью(DynamicRules)
    // например, сохранение, обновление или удаление будут применяться к объектам Condition.
    //orphanRemoval = true: Указывает, что если объект из списка conditions был удален-не связан с объектом DynamicRule,
    // то этот объект будет удален из базы данных (становится "сиротой").
    @JoinColumn(name = "dynamic_rule_id")
    private List<ConditionElementsRules> conditions;
    @Version
    private Long version;//при обновлении проверить объект. Если это не DynamicRules, то приостановит процесс обновления

    public DynamicRules(String productName, String productText, UUID productId, List<ConditionElementsRules> conditions) {
        this.productName = productName;
        this.productText = productText;
        this.productId = productId;
        this.conditions = conditions;
    }

    public Recommendation extractRecommendation(){
        return new Recommendation( productName,productId, productText);
    }


    public List<ConditionElementsRules> getConditions() {

        return conditions;
    }

    public void setConditions(List<ConditionElementsRules> conditions) {
        this.conditions = conditions;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getProductText() {
        return productText;
    }

    public void setProductText(String productText) {
        this.productText = productText;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) return false;
        DynamicRules that = (DynamicRules) o;
        return Objects.equals(id, that.id) && Objects.equals(productName, that.productName) && Objects.equals(productText, that.productText) && Objects.equals(productId, that.productId) && Objects.equals(conditions, that.conditions) && Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, productText, productId, conditions, version);
    }
}
