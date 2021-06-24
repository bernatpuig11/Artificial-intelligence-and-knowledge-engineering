import java.util.ArrayList;

public class Algorithm {

    public boolean AC3(Model m) {
        ArrayList<Constraint> queue = new ArrayList<>(m.constraints);
        Constraint aux;
        for (Constraint c : m.constraints) {
            if (c.typeConstraint == 1) aux = new Constraint(1, c.x2, c.type2, c.x1, c.type1);
            else if (c.typeConstraint == 2) aux = new Constraint(2, c.x2, c.type2, c.x1, c.type1);
            else if (c.typeConstraint == 3) aux = new Constraint(4, c.x2, c.type2, c.x1, c.type1);
            else aux = new Constraint(3, c.x2, c.type2, c.x1, c.type1);
            if (c.typeConstraint != 5) queue.add(aux);
        }

        int x =  0;
        while (!queue.isEmpty()) {
            ++x;
            Constraint c = queue.get(0);
            queue.remove(0);
            if (removeValues(c, m)) {
                if (c.type1 == 1) if (m.Colors.get(c.x1).getHouse().isEmpty()) return false;
                else if (c.type1 == 2) if (m.Nationalities.get(c.x1).getHouse().isEmpty()) return false;
                else if (c.type1 == 3) if (m.Animals.get(c.x1).getHouse().isEmpty()) return false;
                else if (c.type1 == 4) if (m.Drinks.get(c.x1).getHouse().isEmpty()) return false;
                else if (m.SmokeBrands.get(c.x1).getHouse().isEmpty()) return false;

                if (m.Colors.get(c.x1).getHouse().size() == 1) eliminateValues(0,m.Colors.get(c.x1).getHouse().get(0), 1, c, m);
                else if (m.Nationalities.get(c.x1).getHouse().size() == 1) eliminateValues(0, m.Nationalities.get(c.x1).getHouse().get(0), 2, c, m);
                else if (m.Animals.get(c.x1).getHouse().size() == 1) eliminateValues(0, m.Animals.get(c.x1).getHouse().get(0), 3, c, m);
                else if (m.Drinks.get(c.x1).getHouse().size() == 1) eliminateValues(0, m.Drinks.get(c.x1).getHouse().get(0), 4, c, m);
                else eliminateValues(0, m.SmokeBrands.get(c.x1).getHouse().get(0), 5, c, m);

                if (m.Colors.get(c.x2).getHouse().size() == 1) eliminateValues(1, m.Colors.get(c.x2).getHouse().get(0), 1, c, m);
                else if (m.Nationalities.get(c.x2).getHouse().size() == 1) eliminateValues(1, m.Nationalities.get(c.x2).getHouse().get(0), 2, c, m);
                else if (m.Animals.get(c.x2).getHouse().size() == 1) eliminateValues(1, m.Animals.get(c.x2).getHouse().get(0), 3, c, m);
                else if (m.Drinks.get(c.x2).getHouse().size() == 1) eliminateValues(1, m.Drinks.get(c.x2).getHouse().get(0), 4, c, m);
                else eliminateValues(1, m.SmokeBrands.get(c.x2).getHouse().get(0), 5, c, m);

                for (int i = 0; i < m.constraints.size(); ++i) {
                    if (c.typeConstraint == 2) {
                        if (c.type1 == m.constraints.get(i).type2) {
                            for (int j = 0; j < queue.size(); ++j) {
                                Constraint c1 = m.constraints.get(i);
                                Constraint c2 = queue.get(j);
                                if (c1.typeConstraint != c2.typeConstraint && c1.type1 != c2.type1 && c1.type2 != c2.type2 && c1.x1 != c2.x1 && c1.x2 != c2.x2) {
                                    queue.add(c1);
                                }
                            }
                        }
                        else if (c.type2 == m.constraints.get(i).type1) {
                            for (int j = 0; j < queue.size(); ++j) {
                                Constraint c1 = m.constraints.get(i);
                                Constraint c2 = queue.get(j);
                                if (c1.typeConstraint != c2.typeConstraint && c1.type1 != c2.type1 && c1.type2 != c2.type2 && c1.x1 != c2.x1 && c1.x2 != c2.x2) {
                                    queue.add(c1);
                                }
                            }
                        }
                    }
                    else {
                        if (c.type1 == m.constraints.get(i).type2) {
                            for (int j = 0; j < queue.size(); ++j) {
                                Constraint c1 = m.constraints.get(i);
                                Constraint c2 = queue.get(j);
                                if (c1.typeConstraint != c2.typeConstraint && c1.type1 != c2.type1 && c1.type2 != c2.type2 && c1.x1 != c2.x1 && c1.x2 != c2.x2) {
                                    queue.add(c1);
                                }
                            }
                        }
                    }
                }

                System.out.println("Arriba aquiiii ---------");
                for (int i = 0; i < 5; ++i) {
                    System.out.println("Color " + i + " --> House number " + m.Colors.get(i).getHouse());
                    System.out.println();
                    System.out.println("Nationality " + i + " --> House number " + m.Nationalities.get(i).getHouse());
                    System.out.println();
                    System.out.println("Animal " + i + " --> House number " + m.Animals.get(i).getHouse());
                    System.out.println();
                    System.out.println("Drink " + i + " --> House number " + m.Drinks.get(i).getHouse());
                    System.out.println();
                    System.out.println("SmokeBrand " + i + " --> House number " + m.SmokeBrands.get(i).getHouse());
                    System.out.println();
                }
            }
        }

        return true;
    }

    private void eliminateValues(int type, int n, int i, Constraint c, Model m) {
        if (i == 1) {
            for (int j = 0; j < 5; ++j) {
                if (type == 0) if (m.Colors.get(c.x1) != m.Colors.get(j) && m.Colors.get(j).getHouse().size() > 1) m.Colors.get(j).getHouse().remove(Integer.valueOf(n));
                else if (m.Colors.get(c.x2) != m.Colors.get(j) && m.Colors.get(j).getHouse().size() > 1) m.Colors.get(j).getHouse().remove(Integer.valueOf(n));
            }
        }
        else if (i == 2) {
            for (int j = 0; j < 5; ++j) {
                if (type == 0) if (m.Nationalities.get(c.x1) != m.Nationalities.get(j) && m.Nationalities.get(j).getHouse().size() > 1) m.Nationalities.get(j).getHouse().remove(Integer.valueOf(n));
                else if (m.Nationalities.get(c.x2) != m.Nationalities.get(j) && m.Nationalities.get(j).getHouse().size() > 1) m.Nationalities.get(j).getHouse().remove(Integer.valueOf(n));
            }
        }
        else if (i == 3) {
            for (int j = 0; j < 5; ++j) {
                if (type == 0) if (m.Animals.get(c.x1) != m.Animals.get(j) && m.Animals.get(j).getHouse().size() > 1) m.Animals.get(j).getHouse().remove(Integer.valueOf(n));
                else if (m.Animals.get(c.x2) != m.Animals.get(j) && m.Animals.get(j).getHouse().size() > 1) m.Animals.get(j).getHouse().remove(Integer.valueOf(n));
            }
        }
        else if (i == 4) {
            for (int j = 0; j < 5; ++j) {
                if (type == 0) if (m.Drinks.get(c.x1) != m.Drinks.get(j) && m.Drinks.get(j).getHouse().size() > 1) m.Drinks.get(j).getHouse().remove(Integer.valueOf(n));
                else if (m.Drinks.get(c.x2) != m.Drinks.get(j) && m.Drinks.get(j).getHouse().size() > 1) m.Drinks.get(j).getHouse().remove(Integer.valueOf(n));
            }
        }
        else {
            for (int j = 0; j < 5; ++j) {
                if (type == 0) if (m.SmokeBrands.get(c.x1) != m.SmokeBrands.get(j) && m.SmokeBrands.get(j).getHouse().size() > 1) m.SmokeBrands.get(j).getHouse().remove(Integer.valueOf(n));
                else if (m.SmokeBrands.get(c.x2) != m.SmokeBrands.get(j) && m.SmokeBrands.get(j).getHouse().size() > 1) m.SmokeBrands.get(j).getHouse().remove(Integer.valueOf(n));
            }
        }

    }

    private boolean removeValues(Constraint c, Model m) {
        boolean valueRemoved;

        valueRemoved = relation(c, m, c.typeConstraint);

        return valueRemoved;
    }

    private boolean relation(Constraint c, Model m, int relation) {
        boolean found = false;
        boolean valueRemoved = false;

        if (c.type1 == 0) {
            if (relation == 5) {
                ArrayList<Integer> valorsBorrar = new ArrayList<>();

                for (int i = 0; i < m.Colors.get(c.x1).getHouse().size(); ++i) {
                    if (m.Colors.get(c.x1).getHouse().get(i) != c.x2) {
                        valorsBorrar.add(m.Colors.get(c.x1).getHouse().get(i));
                        valueRemoved = true;
                    }
                }

                for(int i : valorsBorrar) {
                    m.Colors.get(c.x1).getHouse().remove(Integer.valueOf(i));
                }
            }
            else if (relation == 2) {
                ArrayList<Integer> valorsBorrar = new ArrayList<>();
                for (int i = 0; i < 5; ++i) valorsBorrar.add(i);

                for (int i = 0; i < m.Colors.get(c.x1).getHouse().size(); ++i) {
                    found = false;

                    int h1 = m.Colors.get(c.x1).getHouse().get(i);

                    for (int j = 0; j < m.Colors.get(c.x2).getHouse().size(); ++j) {
                        int h2 = m.Colors.get(c.x2).getHouse().get(j);
                        if (h1 - h2 == 1 || h2 - h1 == 1) {
                            valorsBorrar.remove(Integer.valueOf(h2));
                            found = true;
                        }
                    }

                    if (!found) {
                        m.Colors.get(c.x1).getHouse().remove(i);
                        valueRemoved = true;
                    }
                }

                for (int i : valorsBorrar) {
                    m.Colors.get(c.x2).getHouse().remove(Integer.valueOf(i));
                    valueRemoved = true;
                }
            }
            else {
                for (int i = 0; i < m.Colors.get(c.x1).getHouse().size(); ++i) {
                    found = false;
                    int h1 = m.Colors.get(c.x1).getHouse().get(i);
                    if (c.type2 == 0) {
                        for (int j = 0; j < m.Colors.get(c.x2).getHouse().size(); ++j) {
                            int h2 = m.Colors.get(c.x2).getHouse().get(j);
                            if (relation == 1) {
                                if (h1 == h2) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 3) {
                                if (h1 - h2 == 1) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 4) {
                                if (h2 - h1 == 1) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                    } else if (c.type2 == 1) {
                        for (int j = 0; j < m.Nationalities.get(c.x2).getHouse().size(); ++j) {
                            int h2 = m.Nationalities.get(c.x2).getHouse().get(j);
                            if (relation == 1) {
                                if (h1 == h2) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 3) {
                                if (h1 - h2 == 1) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 4) {
                                if (h2 - h1 == 1) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                    } else if (c.type2 == 2) {
                        for (int j = 0; j < m.Animals.get(c.x2).getHouse().size(); ++j) {
                            int h2 = m.Animals.get(c.x2).getHouse().get(j);
                            if (relation == 1) {
                                if (h1 == h2) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 3) {
                                if (h1 - h2 == 1) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 4) {
                                if (h2 - h1 == 1) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                    } else if (c.type2 == 3) {
                        for (int j = 0; j < m.Drinks.get(c.x2).getHouse().size(); ++j) {
                            int h2 = m.Drinks.get(c.x2).getHouse().get(j);
                            if (relation == 1) {
                                if (h1 == h2) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 3) {
                                if (h1 - h2 == 1) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 4) {
                                if (h2 - h1 == 1) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                    } else if (c.type2 == 4) {
                        for (int j = 0; j < m.SmokeBrands.get(c.x2).getHouse().size(); ++j) {
                            int h2 = m.SmokeBrands.get(c.x2).getHouse().get(j);
                            if (relation == 1) {
                                if (h1 == h2) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 3) {
                                if (h1 - h2 == 1) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 4) {
                                if (h2 - h1 == 1) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (!found) {
                        m.Colors.get(c.x1).getHouse().remove(i);
                        valueRemoved = true;
                    }
                }
            }
        }
        else if (c.type1 == 1) {
            if (relation == 5) {
                ArrayList<Integer> valorsBorrar = new ArrayList<>();

                for (int i = 0; i < m.Nationalities.get(c.x1).getHouse().size(); ++i) {
                    if (m.Nationalities.get(c.x1).getHouse().get(i) != c.x2) {
                        valorsBorrar.add(m.Nationalities.get(c.x1).getHouse().get(i));
                        valueRemoved = true;
                    }
                }

                for(int i : valorsBorrar) {
                    m.Nationalities.get(c.x1).getHouse().remove(Integer.valueOf(i));
                }
            }
            else if (relation == 2) {
                ArrayList<Integer> valorsBorrar = new ArrayList<>();
                for (int i = 0; i < 5; ++i) valorsBorrar.add(i);

                for (int i = 0; i < m.Nationalities.get(c.x1).getHouse().size(); ++i) {
                    found = false;

                    int h1 = m.Nationalities.get(c.x1).getHouse().get(i);

                    for (int j = 0; j < m.Nationalities.get(c.x2).getHouse().size(); ++j) {
                        int h2 = m.Nationalities.get(c.x2).getHouse().get(j);
                        if (h1 - h2 == 1 || h2 - h1 == 1) {
                            valorsBorrar.remove(Integer.valueOf(h2));
                            found = true;
                        }
                    }

                    if (!found) {
                        m.Nationalities.get(c.x1).getHouse().remove(i);
                        valueRemoved = true;
                    }
                }

                for (int i : valorsBorrar) {
                    m.Nationalities.get(c.x2).getHouse().remove(Integer.valueOf(i));
                    valueRemoved = true;
                }
            }
            else {
                for (int i = 0; i < m.Nationalities.get(c.x1).getHouse().size(); ++i) {
                    found = false;
                    int h1 = m.Nationalities.get(c.x1).getHouse().get(i);
                    if (c.type2 == 0) {
                        for (int j = 0; j < m.Colors.get(c.x2).getHouse().size(); ++j) {
                            int h2 = m.Colors.get(c.x2).getHouse().get(j);
                            if (relation == 1) {
                                if (h1 == h2) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 3) {
                                if (h1 - h2 == 1) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 4) {
                                if (h2 - h1 == 1) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                    } else if (c.type2 == 1) {
                        for (int j = 0; j < m.Nationalities.get(c.x2).getHouse().size(); ++j) {
                            int h2 = m.Nationalities.get(c.x2).getHouse().get(j);
                            if (relation == 1) {
                                if (h1 == h2) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 3) {
                                if (h1 - h2 == 1) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 4) {
                                if (h2 - h1 == 1) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                    } else if (c.type2 == 2) {
                        for (int j = 0; j < m.Animals.get(c.x2).getHouse().size(); ++j) {
                            int h2 = m.Animals.get(c.x2).getHouse().get(j);
                            if (relation == 1) {
                                if (h1 == h2) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 3) {
                                if (h1 - h2 == 1) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 4) {
                                if (h2 - h1 == 1) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                    } else if (c.type2 == 3) {
                        for (int j = 0; j < m.Drinks.get(c.x2).getHouse().size(); ++j) {
                            int h2 = m.Drinks.get(c.x2).getHouse().get(j);
                            if (relation == 1) {
                                if (h1 == h2) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 3) {
                                if (h1 - h2 == 1) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 4) {
                                if (h2 - h1 == 1) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                    } else if (c.type2 == 4) {
                        for (int j = 0; j < m.SmokeBrands.get(c.x2).getHouse().size(); ++j) {
                            int h2 = m.SmokeBrands.get(c.x2).getHouse().get(j);
                            if (relation == 1) {
                                if (h1 == h2) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 3) {
                                if (h1 - h2 == 1) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 4) {
                                if (h2 - h1 == 1) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (!found) {
                        m.Nationalities.get(c.x1).getHouse().remove(i);
                        valueRemoved = true;
                    }
                }
            }
        }
        else if (c.type1 == 2) {
            if (relation == 5) {
                ArrayList<Integer> valorsBorrar = new ArrayList<>();

                for (int i = 0; i < m.Animals.get(c.x1).getHouse().size(); ++i) {
                    if (m.Animals.get(c.x1).getHouse().get(i) != c.x2) {
                        valorsBorrar.add(m.Animals.get(c.x1).getHouse().get(i));
                        valueRemoved = true;
                    }
                }

                for(int i : valorsBorrar) {
                    m.Animals.get(c.x1).getHouse().remove(Integer.valueOf(i));
                }
            }
            else if (relation == 2) {
                ArrayList<Integer> valorsBorrar = new ArrayList<>();
                for (int i = 0; i < 5; ++i) valorsBorrar.add(i);

                for (int i = 0; i < m.Animals.get(c.x1).getHouse().size(); ++i) {
                    found = false;

                    int h1 = m.Animals.get(c.x1).getHouse().get(i);

                    for (int j = 0; j < m.Animals.get(c.x2).getHouse().size(); ++j) {
                        int h2 = m.Animals.get(c.x2).getHouse().get(j);
                        if (h1 - h2 == 1 || h2 - h1 == 1) {
                            valorsBorrar.remove(Integer.valueOf(h2));
                            found = true;
                        }
                    }

                    if (!found) {
                        m.Animals.get(c.x1).getHouse().remove(i);
                        valueRemoved = true;
                    }
                }

                for (int i : valorsBorrar) {
                    m.Animals.get(c.x2).getHouse().remove(Integer.valueOf(i));
                    valueRemoved = true;
                }
            }
            else {
                for (int i = 0; i < m.Animals.get(c.x1).getHouse().size(); ++i) {
                    found = false;
                    int h1 = m.Animals.get(c.x1).getHouse().get(i);
                    if (c.type2 == 0) {
                        for (int j = 0; j < m.Colors.get(c.x2).getHouse().size(); ++j) {
                            int h2 = m.Colors.get(c.x2).getHouse().get(j);
                            if (relation == 1) {
                                if (h1 == h2) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 3) {
                                if (h1 - h2 == 1) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 4) {
                                if (h2 - h1 == 1) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                    } else if (c.type2 == 1) {
                        for (int j = 0; j < m.Nationalities.get(c.x2).getHouse().size(); ++j) {
                            int h2 = m.Nationalities.get(c.x2).getHouse().get(j);
                            if (relation == 1) {
                                if (h1 == h2) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 3) {
                                if (h1 - h2 == 1) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 4) {
                                if (h2 - h1 == 1) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                    } else if (c.type2 == 2) {
                        for (int j = 0; j < m.Animals.get(c.x2).getHouse().size(); ++j) {
                            int h2 = m.Animals.get(c.x2).getHouse().get(j);
                            if (relation == 1) {
                                if (h1 == h2) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 3) {
                                if (h1 - h2 == 1) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 4) {
                                if (h2 - h1 == 1) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                    } else if (c.type2 == 3) {
                        for (int j = 0; j < m.Drinks.get(c.x2).getHouse().size(); ++j) {
                            int h2 = m.Drinks.get(c.x2).getHouse().get(j);
                            if (relation == 1) {
                                if (h1 == h2) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 3) {
                                if (h1 - h2 == 1) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 4) {
                                if (h2 - h1 == 1) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                    } else if (c.type2 == 4) {
                        for (int j = 0; j < m.SmokeBrands.get(c.x2).getHouse().size(); ++j) {
                            int h2 = m.SmokeBrands.get(c.x2).getHouse().get(j);
                            if (relation == 1) {
                                if (h1 == h2) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 3) {
                                if (h1 - h2 == 1) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 4) {
                                if (h2 - h1 == 1) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (!found) {
                        m.Animals.get(c.x1).getHouse().remove(i);
                        valueRemoved = true;
                    }
                }
            }
        }
        else if (c.type1 == 3) {
            if (relation == 5) {
                ArrayList<Integer> valorsBorrar = new ArrayList<>();

                for (int i = 0; i < m.Drinks.get(c.x1).getHouse().size(); ++i) {
                    if (m.Drinks.get(c.x1).getHouse().get(i) != c.x2) {
                        valorsBorrar.add(m.Drinks.get(c.x1).getHouse().get(i));
                        valueRemoved = true;
                    }
                }

                for(int i : valorsBorrar) {
                    m.Drinks.get(c.x1).getHouse().remove(Integer.valueOf(i));
                }
            }
            else if (relation == 2) {
                ArrayList<Integer> valorsBorrar = new ArrayList<>();
                for (int i = 0; i < 5; ++i) valorsBorrar.add(i);

                for (int i = 0; i < m.Drinks.get(c.x1).getHouse().size(); ++i) {
                    found = false;

                    int h1 = m.Drinks.get(c.x1).getHouse().get(i);

                    for (int j = 0; j < m.Drinks.get(c.x2).getHouse().size(); ++j) {
                        int h2 = m.Drinks.get(c.x2).getHouse().get(j);
                        if (h1 - h2 == 1 || h2 - h1 == 1) {
                            valorsBorrar.remove(Integer.valueOf(h2));
                            found = true;
                        }
                    }

                    if (!found) {
                        m.Drinks.get(c.x1).getHouse().remove(i);
                        valueRemoved = true;
                    }
                }

                for (int i : valorsBorrar) {
                    m.Drinks.get(c.x2).getHouse().remove(Integer.valueOf(i));
                    valueRemoved = true;
                }
            }
            else {
                for (int i = 0; i < m.Drinks.get(c.x1).getHouse().size(); ++i) {
                    found = false;
                    int h1 = m.Drinks.get(c.x1).getHouse().get(i);
                    if (c.type2 == 0) {
                        for (int j = 0; j < m.Colors.get(c.x2).getHouse().size(); ++j) {
                            int h2 = m.Colors.get(c.x2).getHouse().get(j);
                            if (relation == 1) {
                                if (h1 == h2) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 3) {
                                if (h1 - h2 == 1) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 4) {
                                if (h2 - h1 == 1) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                    } else if (c.type2 == 1) {
                        for (int j = 0; j < m.Nationalities.get(c.x2).getHouse().size(); ++j) {
                            int h2 = m.Nationalities.get(c.x2).getHouse().get(j);
                            if (relation == 1) {
                                if (h1 == h2) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 3) {
                                if (h1 - h2 == 1) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 4) {
                                if (h2 - h1 == 1) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                    } else if (c.type2 == 2) {
                        for (int j = 0; j < m.Animals.get(c.x2).getHouse().size(); ++j) {
                            int h2 = m.Animals.get(c.x2).getHouse().get(j);
                            if (relation == 1) {
                                if (h1 == h2) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 3) {
                                if (h1 - h2 == 1) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 4) {
                                if (h2 - h1 == 1) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                    } else if (c.type2 == 3) {
                        for (int j = 0; j < m.Drinks.get(c.x2).getHouse().size(); ++j) {
                            int h2 = m.Drinks.get(c.x2).getHouse().get(j);
                            if (relation == 1) {
                                if (h1 == h2) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 3) {
                                if (h1 - h2 == 1) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 4) {
                                if (h2 - h1 == 1) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                    } else if (c.type2 == 4) {
                        for (int j = 0; j < m.SmokeBrands.get(c.x2).getHouse().size(); ++j) {
                            int h2 = m.SmokeBrands.get(c.x2).getHouse().get(j);
                            if (relation == 1) {
                                if (h1 == h2) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 3) {
                                if (h1 - h2 == 1) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 4) {
                                if (h2 - h1 == 1) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (!found) {
                        m.Drinks.get(c.x1).getHouse().remove(i);
                        valueRemoved = true;
                    }
                }
            }
        }
        else {
            if (relation == 5) {
                ArrayList<Integer> valorsBorrar = new ArrayList<>();

                for (int i = 0; i < m.SmokeBrands.get(c.x1).getHouse().size(); ++i) {
                    if (m.SmokeBrands.get(c.x1).getHouse().get(i) != c.x2) {
                        valorsBorrar.add(m.SmokeBrands.get(c.x1).getHouse().get(i));
                        valueRemoved = true;
                    }
                }

                for(int i : valorsBorrar) {
                    m.SmokeBrands.get(c.x1).getHouse().remove(Integer.valueOf(i));
                }
            }
            else if (relation == 2) {
                ArrayList<Integer> valorsBorrar = new ArrayList<>();
                for (int i = 0; i < 5; ++i) valorsBorrar.add(i);

                for (int i = 0; i < m.SmokeBrands.get(c.x1).getHouse().size(); ++i) {
                    found = false;

                    int h1 = m.SmokeBrands.get(c.x1).getHouse().get(i);

                    for (int j = 0; j < m.SmokeBrands.get(c.x2).getHouse().size(); ++j) {
                        int h2 = m.SmokeBrands.get(c.x2).getHouse().get(j);
                        if (h1 - h2 == 1 || h2 - h1 == 1) {
                            valorsBorrar.remove(Integer.valueOf(h2));
                            found = true;
                        }
                    }

                    if (!found) {
                        m.SmokeBrands.get(c.x1).getHouse().remove(i);
                        valueRemoved = true;
                    }
                }

                for (int i : valorsBorrar) {
                    m.SmokeBrands.get(c.x2).getHouse().remove(Integer.valueOf(i));
                    valueRemoved = true;
                }
            }
            else {
                for (int i = 0; i < m.SmokeBrands.get(c.x1).getHouse().size(); ++i) {
                    found = false;
                    int h1 = m.SmokeBrands.get(c.x1).getHouse().get(i);
                    if (c.type2 == 0) {
                        for (int j = 0; j < m.Colors.get(c.x2).getHouse().size(); ++j) {
                            int h2 = m.Colors.get(c.x2).getHouse().get(j);
                            if (relation == 1) {
                                if (h1 == h2) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 3) {
                                if (h1 - h2 == 1) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 4) {
                                if (h2 - h1 == 1) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                    } else if (c.type2 == 1) {
                        for (int j = 0; j < m.Nationalities.get(c.x2).getHouse().size(); ++j) {
                            int h2 = m.Nationalities.get(c.x2).getHouse().get(j);
                            if (relation == 1) {
                                if (h1 == h2) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 3) {
                                if (h1 - h2 == 1) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 4) {
                                if (h2 - h1 == 1) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                    } else if (c.type2 == 2) {
                        for (int j = 0; j < m.Animals.get(c.x2).getHouse().size(); ++j) {
                            int h2 = m.Animals.get(c.x2).getHouse().get(j);
                            if (relation == 1) {
                                if (h1 == h2) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 3) {
                                if (h1 - h2 == 1) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 4) {
                                if (h2 - h1 == 1) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                    } else if (c.type2 == 3) {
                        for (int j = 0; j < m.Drinks.get(c.x2).getHouse().size(); ++j) {
                            int h2 = m.Drinks.get(c.x2).getHouse().get(j);
                            if (relation == 1) {
                                if (h1 == h2) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 3) {
                                if (h1 - h2 == 1) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 4) {
                                if (h2 - h1 == 1) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                    } else if (c.type2 == 4) {
                        for (int j = 0; j < m.SmokeBrands.get(c.x2).getHouse().size(); ++j) {
                            int h2 = m.SmokeBrands.get(c.x2).getHouse().get(j);
                            if (relation == 1) {
                                if (h1 == h2) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 3) {
                                if (h1 - h2 == 1) {
                                    found = true;
                                    break;
                                }
                            } else if (relation == 4) {
                                if (h2 - h1 == 1) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (!found) {
                        m.SmokeBrands.get(c.x1).getHouse().remove(i);
                        valueRemoved = true;
                    }
                }
            }
        }
        return valueRemoved;
    }
}
