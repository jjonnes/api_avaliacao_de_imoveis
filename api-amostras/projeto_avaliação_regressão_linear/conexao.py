import pandas as pd
from sqlalchemy import create_engine


#Conexão ao Banco de Dados e Extração de Dados
def conectar_banco():
    uri = 'mysql+mysqlconnector://root:zxcvbnm123@localhost:3306/amostras_avaliacao'
    engine = create_engine(uri)
    return engine
