if ./mvnw clean install > output.txt 2>&1; then
    echo "Proyecto construido exitosamente."
    rm -rf output.txt
    open target/site/jacoco/index.html
else
    echo "Error al construir el proyecto. Espera instrucciones para continuar."
    { echo "<pre>"; cat output.txt; echo "</pre>"; } > target/output.html
    rm -rf output.txt
    open target/output.html
fi