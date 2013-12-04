#include <stdio.h>
#include <stdlib.h>
#define MAX 1000
#define SIZESTDIN 81

void initVector(int *table, int n){
    int i;
    for(i = 0; i < n; i++){
        table[i] = -1;    
    } 
}

/*função de hash utilizada para mapear*/

int hashFunction(int i, int j, int k){
    return i*100+j*10+k;
}

/*Recebe um vetor e mapeia cada uma das variaveis em seus valores ficticios
para valores no formato cnf de arquivo*/

void mapTable(int *table){
    int i, j, k, pos;
    pos = 1;   
    for(i = 1; i <= 9; i++){
	    for(j = 1; j<= 9; j++){
	      for(k = 1; k <= 9; k++){
	    	table[hashFunction(i,j,k)] = pos;
		pos++;
	      }	
	    }
    }
}

void testTable(int *table){
    int i, count;
    count = 0;
    for(i = 0; i <= MAX; i++){
    if(table[i] > 0) count++;    
    }
    if(count == 729){ 
        printf ("Teste: Verifica número de variáveis na Tabela ---> OK!\n");
    }
    else { 
        printf ("Teste: Verifica número de variáveis na Tabela ---> FAIL!\n");
    }
}

/*cria clausula para garantir a existencia de pelo menos um valor para cada posição do sudoko*/

void atLeastOneNumberForPosition(int *table){
    int i, j, k;   
    for(i = 1; i <= 9; i++){
      for(j = 1; j<= 9; j++){
	for(k = 1; k <= 9; k++){
	  printf( "%d ",table[hashFunction( i, j , k)]); 
	}
	printf( "0\n");	
      }
    }
}

/*cria clausulas para garantir que não existam mais de uma variavel verdadeira 
para uma mesma posição do sudoko*/

void noSameNumbersAtSamePosition(int *table){
   int i, j, k, l;   
    for(i = 1; i <= 9; i++){
	    for(j = 1; j<= 9; j++){
	    	for(k = 1; k <= 9; k++){
	    	   for(l = k; l <= 9; l++){
		         if(l != k)
		            printf( "%d %d 0\n", -1*table[hashFunction( i, j, k)], -1*table[hashFunction( i, j, l)]);
	    	   }
	    	}
      }
    }
}

/*cria clausulas para garantir que nao existam variaveis logicas com os mesmos valores
em uma mesma linha do sudoko*/

void noSameNumbersAtSameLine( int *table){
  int i,j,k,c; 
  for(i = 1; i <= 9; i++){
    for(j = 1; j <= 9; j++){
      for(k = 1; k <= 9; k++){
      	for(c = j; c <= 9; c++){
      	  if(c != j)
      	    printf( "%d %d 0\n", -1*table[hashFunction( i, j, k)], -1*table[hashFunction( i, c, k)]);
      	}
      }
    }
  }
}


/*cria clausulas para garantir que nao exista em uma mesma coluna duas variaveis
que correspondam a numeros iguais*/

void noSameNumbersAtSameColum( int *table){
  int i,j,k,c; 
  for(i = 1; i <= 9; i++){
    for(j = 1; j <= 9; j++){
      for(k = 1; k <= 9; k++){
         for(c = i; c <= 9; c++){
            if(c != i)
               printf( "%d %d 0\n", -1*table[hashFunction( i, j, k)], -1*table[hashFunction( c, j , k)]);	    
         	}
      }
    }
  }
}

/*recebe uma posição x,y do sudoko e devolve procura a zona do sudoko
onde esta variavel esta localizada. Retorna a posição superior esquerda
, da respectiva zona, como novos valores de x e y*/

int zoneFinder( int *x, int *y){
  int zone,i,j;
  zone = -1;
  i = *x, j = *y;
  if(i <= 3 && j <= 3) {
    zone = 1, *x = 1, *y = 1;
  }
  else if(i <= 3 && j <= 6){
    zone = 2, *x = 1, *y = 4;
  }
  else if(i <= 3 && j <= 9){
    zone = 3, *x = 1, *y = 7;
  }
  else if(i <= 6 && j <= 3){
    zone = 4, *x = 4, *y = 1;
  }
  else if(i <= 6 && j <= 6){
    zone = 5, *x = 4, *y = 4;
  }
  else if(i <= 6 && j <= 9){
    zone = 6, *x = 4, *y = 7;
  }
  else if(i <= 9 && j <= 3){
    zone = 7, *x = 7, *y = 1;
  }
  else if(i <= 9 && j <= 6){
    zone = 8, *x = 7, *y = 4;
  }
  else if(i <= 9 && j <= 9){
    zone = 9; *x = 7, *y = 7;
  }
  return zone;
}
/*cria clausulas para garantir que não existam duas variaveis , que correspondam a um
mesmo número, dentro da mesma zona*/

void noSameNumberInSameSquare( int r, int s, int k, int *table) {
  int zone,i,j,x,y;
   zone = 0;
   x = r, y = s;
   zone = zoneFinder(&x,&y);
   j = s;
   for(i = r; i < x+3; i++){
     while(j < y+3){
       if(i != r && j != s)
	 printf( "%d %d 0\n", -1*table[hashFunction( r, s , k)], -1*table[hashFunction( i, j, k )]);
       j++;
     }
     j = y;
   }
}
		 
								       
void createClauses( int *table){
  int i,j,k;
  for( i = 1; i <= 9; i++){
    for( j = 1; j <= 9; j++){
      for( k = 1; k <= 9; k++){
	noSameNumberInSameSquare( i, j, k, table);
      }
    }
  }
}

/*void testZoneFinder() {
  int i,j;
  for(i = 1; i <= 9; i++){
    for(j = 1; j <= 9; j++){
      printf("(%d,%d) = %d\n",i,j, zoneFinder(i,j));
    }
  }
  }*/

/*cria uma clausula para cada entrada do stdin de todos valores maiores que 0
para garantir que essas variáveis sejam verdadeiras*/

int readStdin(int *table, int *v){
  int i,j,var,clause,count;
  count = 0;
  for(i = 1; i <= 9 ; i++){
    for(j = 1; j <= 9; j++){
      scanf( "%d", &var);
      if(var > 0){
	v[count] = table[hashFunction(i,j,var)];
	count++;
      }
    }     
  }
  return count;
}

void printFirstLine( int extra){
  printf("p cnf 729 %d\n", 10287+extra);
}

void printVectorToN( int *v, int n){
  int i;
  for( i = 0; i < n; i++){
    printf( "%d 0\n", v[i]);
  }
}
int main( int argc, char **argv) {
  int *table,*v;
  int size;
  size = 0;
  table = malloc( MAX*sizeof( int));
  v = malloc( SIZESTDIN*sizeof( int));
  
  initVector( table, MAX);
  initVector( v, SIZESTDIN);
  mapTable( table);
  size = readStdin( table, v);
  printFirstLine( size);
  printVectorToN( v, size);
  atLeastOneNumberForPosition( table);
  noSameNumbersAtSamePosition( table);
  noSameNumbersAtSameLine( table);
  noSameNumbersAtSameColum( table);
  createClauses( table);
  
  return 0;
}
