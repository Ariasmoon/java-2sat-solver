# Le nom de votre classe principale
# Renommez si nécessaire
MAINCLASS=Main
## Le chemin vers où votre classe compilée est installée
# Renommez si nécessaire
INSTALLDIR=../out/production/tp1 Souvleris Allouche _

# INSTALLATION COURANTE
JAVAC=javac


MAINDIR=$(dir $(subst .,/,$(MAINCLASS)))

compile: $(subst .,/,$(MAINCLASS)).class

%.class: %.java
	$(JAVAC) -g -d ../$(INSTALLDIR) $*.java

clean:
	- rm $(MAINDIR)*.class

install: 
	- cp -r $(MAINDIR)*.class ../$(INSTALLDIR)/$(MAINDIR)

cleanInstall:
	rm  ../$(INSTALLDIR)/$(MAINDIR)*.class