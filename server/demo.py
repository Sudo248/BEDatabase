import math
def isPrime(n):
    if n < 2: return False
    for i in range(2, int(math.sqrt(n))+1):
        if n % i == 0: return False
    return True

def primeList(list):
    res = []
    for i in list:
        if isPrime(i):
            res.append(i)
    return res

if __name__ == "__main__":
    print(primeList([11,12,13,14,15]))

