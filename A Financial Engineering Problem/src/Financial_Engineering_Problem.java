import gurobi.*;

public class Financial_Engineering_Problem {
	public static void main(String[] args) throws GRBException {
		// Auto-generated method stub
		GRBEnv env = new GRBEnv("Problem5.log");
		GRBModel model = new GRBModel(env);

		// Create decision variables
		GRBVar x1 = model.addVar(0.0, GRB.INFINITY, 0.07, GRB.CONTINUOUS, "X_Bond");
		GRBVar x2 = model.addVar(0.0, GRB.INFINITY, 0.08, GRB.CONTINUOUS, "X_Cd");
		GRBVar x3 = model.addVar(0.0, GRB.INFINITY, 0.12, GRB.CONTINUOUS, "X_Risky");

		// Objective function
		model.set(GRB.IntAttr.ModelSense, GRB.MAXIMIZE);

		// Constraints
		GRBLinExpr expr = new GRBLinExpr();

		// Constraint 1: Budget
		expr = new GRBLinExpr();
		expr.addTerm(1.0, x1);
		expr.addTerm(1.0, x2);
		expr.addTerm(1.0, x3);

		model.addConstr(expr, GRB.LESS_EQUAL, 12000, "Budget");

		// Constraint 2: Maximum Risk for Investment
		expr = new GRBLinExpr();
		expr.addTerm(1.0, x3);

		model.addConstr(expr, GRB.LESS_EQUAL, 2000, "Maximum Risky Investment");

		// Constraint 3: Tax Ratio
		expr = new GRBLinExpr();
		expr.addTerm(1.0, x1);
		expr.addTerm(-3.0, x2);

		model.addConstr(expr, GRB.GREATER_EQUAL, 0, "Tax Ratio");
		
		// Optimize
		model.optimize();
		
		// Objective Function Values
		System.out.println("Objective Value is: " + model.get(GRB.DoubleAttr.ObjVal));
		
		// Optimal Solution Values
		System.out.println();
		System.out.println(x1.get(GRB.StringAttr.VarName) + ": " + x1.get(GRB.DoubleAttr.X));
		System.out.println(x2.get(GRB.StringAttr.VarName) + ": " + x1.get(GRB.DoubleAttr.X));
		System.out.println(x3.get(GRB.StringAttr.VarName) + ": " + x1.get(GRB.DoubleAttr.X));
		
		// Binding Constraints
		for(GRBConstr c : model.getConstrs()) {
			System.out.println();
			System.out.println("Slack variable for " + c.get(GRB.StringAttr.ConstrName) + " is: " + c.get(GRB.DoubleAttr.Slack));
			if(c.get(GRB.DoubleAttr.Slack) == 0.0) {
				System.out.println("Slack variable for " + c.get(GRB.StringAttr.ConstrName) + " is binding");
			}
		}
		
		// Reduced Cost
		System.out.println();
		System.out.println("Reduced cost for " + x1.get(GRB.StringAttr.VarName) + " is: " + x1.get(GRB.DoubleAttr.RC));
		System.out.println("Reduced cost for " + x2.get(GRB.StringAttr.VarName) + " is: " + x2.get(GRB.DoubleAttr.RC));
		System.out.println("Reduced cost for " + x3.get(GRB.StringAttr.VarName) + " is: " + x3.get(GRB.DoubleAttr.RC));
		
		// Shadow Prices
		System.out.println();
		for(GRBConstr c : model.getConstrs()) {
			System.out.println("Shadow price for " + c.get(GRB.StringAttr.ConstrName) + " is: " + c.get(GRB.DoubleAttr.Pi));
		}
	}
}
