# Exemplo de programa Python no VS Code

# Função simples que soma dois números
def somar(a, b):
    return a + b

# Solicita ao usuário que insira dois números
num1 = float(input("Digite o primeiro número: "))
num2 = float(input("Digite o segundo número: "))

# Chama a função somar e imprime o resultado
resultado = somar(num1, num2)
print("A soma de", num1, "e", num2, "é", resultado)
