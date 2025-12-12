# 🧠 Smart Product Pricing Challenge – Solution Documentation

## 1. Methodology

Our goal in this challenge was to predict product prices accurately by leveraging both **visual** and **textual** product information. Since product images often contain valuable visual cues, we built a workflow that integrates **computer vision (CV)** and **natural language processing (NLP)** techniques.

Rather than directly processing raw images through computationally intensive CNN architectures, we first used an **image captioning model** to generate descriptive textual summaries of the product images. These captions captured essential visual details such as **color, shape, packaging, and quantity**.

We then combined these captions with the **catalog text data** — including product titles, brand names, and descriptions — to form a unified text representation.  
This text fusion provided a complete semantic context for each product, representing both its **visual** and **descriptive** aspects.

Finally, we embedded this merged text using a **multilingual transformer model** to produce dense semantic vectors.  
These embeddings effectively captured relationships between products and were used as input features for a **regression model** that estimated product prices.

> This hybrid multimodal pipeline ensured that both textual semantics and visual attributes jointly influenced the final price predictions.

---

## 2. Model Architecture and Approach

### a. Image Captioning

For generating captions, we employed the **Salesforce BLIP Image Captioning Model** (`Salesforce/blip-image-captioning-base`) — a vision-language model known for its fluency and descriptive accuracy.

- Each image was processed through **BLIP** to produce concise, human-like captions (e.g., *"a red shampoo bottle labeled herbal essence"*).
- These captions conveyed visual elements often missing from textual listings.
- The captions were cleaned by removing punctuation, converting to lowercase, and applying consistent formatting.

---

### b. Text Embeddings

The **caption**, **product title**, and **description** were concatenated into a single text string and embedded using the **Sentence Transformers** model:  
`paraphrase-multilingual-MiniLM-L12-v2`.

**Reasons for model selection:**
- Strong multilingual capability (handles mixed-language product data)
- Excellent performance on semantic similarity tasks
- Lightweight and efficient for large datasets

The resulting **fixed-length dense vectors** represented the overall meaning of each product based on both text and visual features.

---

### c. Price Prediction Model

The embeddings served as input to a **CatBoost Regressor**, a gradient boosting model well-suited for structured data.

**Reasons for choosing CatBoost:**
- Handles non-linear relationships effectively  
- Resistant to overfitting  
- Built-in support for categorical features  

To stabilize training and reduce the effect of skewed price distributions:
- **Log transformation** was applied to prices before training.
- Predictions were **exponentiated back** to original values post-training.

---

## 3. Feature Engineering

To improve model performance, several preprocessing and feature enhancement techniques were implemented:

- **Text Cleaning:** Removed unnecessary symbols, stopwords, and redundant words.  
- **Attribute Extraction:** Encoded features like “bundle,” “pack,” “set,” and quantity indicators (e.g., *500ml*, *2 pcs*).  
- **Visual Augmentation:** Used image-generated captions to capture color, packaging, and design cues.  
- **Missing Image Handling:** Added retry mechanisms and logging to reduce data loss from failed downloads.  
- **Feature Normalization:** Ensured uniform feature scales for all model inputs.

These steps allowed the model to identify subtle patterns, such as how packaging, quantity, or brand influence pricing.

---

## 4. Training and Validation

The pipeline was trained using **75,000 product samples** from the competition dataset.  
An **80/20 stratified validation split** maintained fair category representation.

**Training Details:**
- **Evaluation Metric:** Symmetric Mean Absolute Percentage Error (SMAPE)  
- **Price Transformation:** Log-transformed target prices for regression stability  
- **Optimization:** Tuned CatBoost hyperparameters — `depth`, `learning_rate`, `L2_regularization`  
- **Early Stopping:** Applied using validation SMAPE scores to avoid overfitting  
- **Post-processing:** Ensured positive floating-point outputs and correct submission formatting

This setup provided a balance between model interpretability and predictive performance.

---

## 5. Results and Model Performance

While the official leaderboard results are confidential, internal validation achieved **low SMAPE scores**, confirming consistency and reliability.

The model successfully captured:
- Price variations based on **packaging size and type**  
- **Visual patterns** associated with premium or multi-pack products  
- **Textual cues** indicating brand quality or reputation  

Multiple cross-validation runs demonstrated **stable and robust** performance across datasets.

---

## 6. Additional Notes

- All experiments strictly followed the competition’s **data usage and integrity policies**.  
- The pipeline is **resource-efficient**, suitable for moderate hardware environments.  
- All models and scripts are compliant with the **Apache 2.0 License**.  
- Final submissions were verified for accuracy and formatting as per challenge requirements.

---

## 7. Future Work and Enhancements

Future improvements can make the system more scalable and accurate:

- **Unified multimodal architectures:** Use models like CLIP for joint text–image learning.  
- **Enhanced image understanding:** Integrate object detection or attribute extraction modules.  
- **Domain fine-tuning:** Adapt embedding models to e-commerce-specific terminology.  
- **Hybrid modeling:** Introduce a preliminary price-range classifier before regression.

---

## 8. References

- [Salesforce BLIP Image Captioning Model](https://huggingface.co/Salesforce/blip-image-captioning-base)  
- [Sentence Transformers Multilingual Model](https://huggingface.co/sentence-transformers/paraphrase-multilingual-MiniLM-L12-v2)  
- [CatBoost Documentation](https://catboost.ai/docs/)  
- Official Challenge Dataset and Rules — *provided by the competition organizers.*

---

## 🏁 Summary

This solution presents a **hybrid multimodal pipeline** combining **image captioning**, **semantic text embeddings**, and **gradient boosting regression**.  
By bridging **visual** and **textual** product data, the system delivers reliable and scalable product price predictions.
