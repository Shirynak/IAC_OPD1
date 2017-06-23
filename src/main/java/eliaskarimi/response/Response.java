
package eliaskarimi.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="result">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="female" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="male" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="total" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "result"
})
@XmlRootElement(name = "response")
public class Response {

    @XmlElement(required = true)
    protected Response.Result result;

    /**
     * Gets the value of the result property.
     * 
     * @return
     *     possible object is
     *     {@link Response.Result }
     *     
     */
    public Response.Result getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     * @param value
     *     allowed object is
     *     {@link Response.Result }
     *     
     */
    public void setResult(Response.Result value) {
        this.result = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="female" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="male" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="total" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "female",
        "male",
        "total"
    })
    public static class Result {

        protected int female;
        protected int male;
        protected int total;

        /**
         * Gets the value of the female property.
         * 
         */
        public int getFemale() {
            return female;
        }

        /**
         * Sets the value of the female property.
         * 
         */
        public void setFemale(int value) {
            this.female = value;
        }

        /**
         * Gets the value of the male property.
         * 
         */
        public int getMale() {
            return male;
        }

        /**
         * Sets the value of the male property.
         * 
         */
        public void setMale(int value) {
            this.male = value;
        }

        /**
         * Gets the value of the total property.
         * 
         */
        public int getTotal() {
            return total;
        }

        /**
         * Sets the value of the total property.
         * 
         */
        public void setTotal(int value) {
            this.total = value;
        }

    }

}
