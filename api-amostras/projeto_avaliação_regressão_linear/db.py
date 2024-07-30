import pandas as pd


def extrair_dados(engine):
    query = """
    SELECT 
        id, cidade, endereco, bairro, 
        area, preco, quartos, banheiros, vagas, url
    FROM 
        amostras
    """
    df = pd.read_sql(query, engine)
    return df