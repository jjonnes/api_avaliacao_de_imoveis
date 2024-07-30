import sys

def input ():
  cidade = sys.argv[1]
  bairro = sys.argv[2]
  area = int(sys.argv[3])
  quartos = int(sys.argv[4])
  banheiros = int(sys.argv[5])
  vagas = int(sys.argv[6])

  return cidade, bairro, area, quartos, banheiros, vagas

